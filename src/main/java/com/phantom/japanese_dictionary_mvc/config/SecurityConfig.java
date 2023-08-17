package com.phantom.japanese_dictionary_mvc.config;

import ch.qos.logback.core.joran.action.NOPAction;
import com.phantom.japanese_dictionary_mvc.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override //SpringSecurity Configuration + authorization
    protected void configure (HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/dictionary/import", "/grammar/import").hasRole("ADMIN")
                .antMatchers("/auth/login", "/error").permitAll()
                .anyRequest().hasAnyRole("ADMIN","USER")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/dictionary", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
    }

    //Authentication options
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
