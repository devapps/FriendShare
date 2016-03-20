package com.friendshare.providers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.friendshare.services.DataService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	DataService dataService;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
 
        // use the credentials to try to authenticate against the third party system
        if (dataService.getUserDetails(name)!=null && dataService.getUserDetails(name).getAppPin().equals(password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_APP"));
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        } else {
            throw new AuthenticationServiceException("Unable to auth against third party systems");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
