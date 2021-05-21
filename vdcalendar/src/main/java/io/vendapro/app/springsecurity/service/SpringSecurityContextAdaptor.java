package io.vendapro.app.springsecurity.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.vendapro.app.springsecurity.core.authority.CalendarUserAuthorityUtils;
import io.vendapro.app.springsecurity.domain.CalendarUser;


@Component
public class SpringSecurityContextAdaptor implements UserContext {


	@Override
	public CalendarUser getCurrentUser() {
		Authentication authenticaton = SecurityContextHolder.getContext().getAuthentication();
		if ( authenticaton == null )
			return null;
		/*
		String email = authenticaton.getName();
		return this.calendaService.findUserByEmail(email);
		*/
		return (CalendarUser) authenticaton.getPrincipal();
	}

	@Override
	/**
	 * at the point login
	 */
	public void setCurrentUser(CalendarUser user) {
		
		// UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
		Collection authorities =
				CalendarUserAuthorityUtils.createAuthorities(user);
		
		// user is the Principal
		Authentication authentication = new UsernamePasswordAuthenticationToken( user, user.getPassword(), authorities );
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

}
