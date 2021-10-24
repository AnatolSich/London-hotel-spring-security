package org.example.auth;

import org.example.entity.User;
import org.example.exception.BlockedException;
import org.example.repository.UserRepository;
import org.example.service.LoginAttemptService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LondonUserDetailsService implements UserDetailsService {


    private final LoginAttemptService loginAttemptService;

    private final HttpServletRequest request;

    private final UserRepository userRepository;

    public LondonUserDetailsService(LoginAttemptService loginAttemptService, HttpServletRequest request, UserRepository userRepository) {
        super();
        this.loginAttemptService = loginAttemptService;
        this.request = request;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //   System.out.println(username);
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            System.out.println("BLOCKED");
            throw new BlockedException("blocked");
        }

        User user = this.userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        //   System.out.println(user.toString());
        return user;
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
