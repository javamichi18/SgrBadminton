package de.soflimo.sgr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.soflimo.sgr.model.Spieler;
import de.soflimo.sgr.repository.SpielerRepository;

/**
 *
 */
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpielerRepository spielerRepository;

    private static final Logger log = LoggerFactory.getLogger(ApplicationSecurity.class);


    @Override
    protected void configure (HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .headers().frameOptions().disable();

        http
            .authorizeRequests()
                //.antMatchers("/").access("hasRole('USER')")
            .antMatchers("/css/**", "/js/**", "/fonts/**", "/pic/**").permitAll()
            .antMatchers("/console/**").permitAll()
            .anyRequest().authenticated()
            .and()

            .formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/login")
                //.defaultSuccessUrl("/sgrbadminton/index.html")
            .permitAll()
            .and()

            .logout()
            .permitAll();
    }


    @Override
    protected void configure (
        AuthenticationManagerBuilder auth) throws Exception {

        UserDetailsService userDetailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername (String username)
                throws UsernameNotFoundException {

                Spieler spieler = spielerRepository.findByEmail(username);
                if (spieler != null) {
                    log.info("user login: "+spieler);
                    return spieler.mapUserDetails();
                }
                throw new UsernameNotFoundException("User '" + username + "' not found.");

            }
        };

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
