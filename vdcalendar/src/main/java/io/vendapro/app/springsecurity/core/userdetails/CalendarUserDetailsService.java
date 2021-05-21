package io.vendapro.app.springsecurity.core.userdetails;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.vendapro.app.springsecurity.core.authority.CalendarUserAuthorityUtils;
import io.vendapro.app.springsecurity.dataaccess.CalendarUserDao;
import io.vendapro.app.springsecurity.domain.CalendarUser;

@Component
public class CalendarUserDetailsService implements UserDetailsService {
	private final CalendarUserDao calendarUserDao;

	@Autowired
	public CalendarUserDetailsService(CalendarUserDao calendarUserDao) {
		this.calendarUserDao = calendarUserDao;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CalendarUser user = calendarUserDao.findUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username/password.");
		}
		// Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);
		return new CalendarUserDetails(user);
	}

	private final class CalendarUserDetails extends CalendarUser implements UserDetails {
		CalendarUserDetails(CalendarUser user) {
			setId(user.getId());
			setEmail(user.getEmail());
			setFirstName(user.getFirstName());
			setLastName(user.getLastName());
			setPassword(user.getPassword());
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return CalendarUserAuthorityUtils.createAuthorities(this);
		}

		public String getUsername() {
			return getEmail();
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isEnabled() {
			return true;
		}
	}

}