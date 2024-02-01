package com.fatema.sample.configs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        List<UserDetails> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public CustomSessionManager customSessionManager() {
        return new CustomSessionManager();
    }
}
