package com.project.enotes_api_service.Security;

import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail((username));
        if(user==null){
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new CustomUserDetails(user);
    }
}
