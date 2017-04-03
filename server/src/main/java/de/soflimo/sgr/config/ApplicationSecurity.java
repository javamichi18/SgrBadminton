package de.soflimo.sgr.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${security.unprotected.resource.paths}")
    private String unprotectedPaths;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] unprotectedPaths = getUnprotectedPaths();
        log.info("UNPROTECTED: " + Arrays.asList(getUnprotectedPaths()));

        http
            .csrf().disable()
            .headers().frameOptions().disable();

        http
            .authorizeRequests()
            .antMatchers(getUnprotectedPaths()).permitAll()
            .anyRequest().authenticated()

            .and()
            .logout()
            .permitAll();

        if (unprotectedPaths == null || !contains(unprotectedPaths, "rest")) {
            http
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                //.defaultSuccessUrl("/sgrbadminton/index.html")
                .permitAll();
        }
    }


    @Override
    protected void configure(
        AuthenticationManagerBuilder auth) throws Exception {

        UserDetailsService userDetailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)
                throws UsernameNotFoundException {

                Spieler spieler = spielerRepository.findByEmail(username);
                if (spieler != null) {
                    log.info("user login: " + spieler);
                    return spieler.mapUserDetails();
                }
                throw new UsernameNotFoundException("User '" + username + "' not found.");

            }
        };

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }


    private boolean contains(String[] unprotectedPaths, String fragment) {

        if (unprotectedPaths == null || unprotectedPaths.length == 0)
            return false;

        for (String path : unprotectedPaths)
            if (path.contains(fragment))
                return true;

        return false;
    }


    private String[] getUnprotectedPaths() {

        List<String> paths = new ArrayList<>();
        if (unprotectedPaths != null && unprotectedPaths.length() > 0) {
            String[] split = unprotectedPaths.split(",");
            for (String val : split)
                paths.add(getPath(val));
        } else
            paths.add(getPath("css"));

        return paths.toArray(new String[0]);
    }


    private String getPath(String p) {
        return "/" + p + "/**";
    }
}
