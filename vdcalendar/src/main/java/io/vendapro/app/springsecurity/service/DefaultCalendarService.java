package io.vendapro.app.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import io.vendapro.app.springsecurity.dataaccess.CalendarUserDao;
import io.vendapro.app.springsecurity.dataaccess.EventDao;
import io.vendapro.app.springsecurity.domain.CalendarUser;
import io.vendapro.app.springsecurity.domain.Event;

/**
 * A default implementation of {@link CalendarService} that delegates to {@link EventDao} and {@link CalendarUserDao}.
 *
 * @author Rob Winch
 *
 */
@Repository
public class DefaultCalendarService implements CalendarService {

    private final EventDao eventDao;
    private final CalendarUserDao userDao;


    
    @Autowired
    public DefaultCalendarService(EventDao eventDao, CalendarUserDao userDao) {
        if (eventDao == null) {
            throw new IllegalArgumentException("eventDao cannot be null");
        }
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        this.eventDao = eventDao;
        this.userDao = userDao;
    }

    public Event getEvent(int eventId) {
        return eventDao.getEvent(eventId);
    }

    public int createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    public List<Event> findForUser(int userId) {
        return eventDao.findForUser(userId);
    }

    public List<Event> getEvents() {
        return eventDao.getEvents();
    }

    public CalendarUser getUser(int id) {
        return userDao.getUser(id);
    }

    public CalendarUser findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public List<CalendarUser> findUsersByEmail(String partialEmail) {
        return userDao.findUsersByEmail(partialEmail);
    }

    public int createUser(CalendarUser user) {
    	// SpringSecurity createUser
    	List<GrantedAuthority>  authorities = AuthorityUtils.createAuthorityList("ROLE+USER");
    	UserDetails userdetails = new User( user.getEmail(), user.getPassword(), authorities);
    	// Removed after customizing CalendarUserDetailsService 
    	// this.userdetailsManager.createUser(userdetails);
    	
        return userDao.createUser(user);
    }
}