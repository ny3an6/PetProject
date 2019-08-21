package com.nikita.simpleProject.config;

import com.nikita.simpleProject.security.JwtTokenFilterConfigurer;
import com.nikita.simpleProject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http

                .authorizeRequests()
                    .antMatchers("/login", "/addUser","/secondDb/**").permitAll()
                    .antMatchers("/h2-console/**/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers( "/main/**").hasRole("USER")
                    .anyRequest().authenticated();

        http.exceptionHandling().accessDeniedPage("/login");

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                .mvcMatchers("/public/**")
                .antMatchers("/webjars/**", "/h2-console/**", "/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html/**", "/swagger-ui.html#/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() throws Exception{
        return new BCryptPasswordEncoder(8);
    }
}

