package fr.gdc.imaker.gateway.Service;
import fr.gdc.imaker.gateway.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = userService.findUserByEmail(username);
        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().getRoleName()));
            MyUserDetails userDetails = new MyUserDetails();
            userDetails.setUser(user);
            userDetails.setAuthorities(authorities);
            return userDetails;

        } else
            throw new AuthenticationCredentialsNotFoundException("Invalid Username or password !!!");
    }
}
