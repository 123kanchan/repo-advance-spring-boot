package com.example.demo1.blog;

import com.example.demo1.service.impl.JWTAuthenticationFilter;
import com.example.demo1.service.impl.JwtAuthenticationEntryPoint;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity //this added for specific api perforing secific operation
@SecurityScheme(
        name="Bear Authentication",
        type= SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme="bearer"
)
public class SecurityConfig {

    //for database aithentication an commented inmemory authentication
    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    //for database aithentication
    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint,JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint=authenticationEntryPoint;
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
    }
    @Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
   return configuration.getAuthenticationManager();
}
    //this  bean for basic authentication how it works
    @Bean
   public  SecurityFilterChain securityfilter(HttpSecurity http) throws Exception{
        //this for no user has anuy restriction to perform opertaion
        //http.csrf((csrf)->csrf.disable()).authorizeHttpRequests((authorize)->authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        //only admin has for all things and orhter users have only for get
        http.csrf((csrf)->csrf.disable()).authorizeHttpRequests((authorize)
                ->authorize.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/api/auth/v1/**").
                permitAll().anyRequest().authenticated()
        ).exceptionHandling((exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
   }

/*   @Bean
   //for in memory authentication multiple users with diff roles you can make
   public UserDetailsService userDetails(){
       UserDetails kanchan= User.builder().username("kanchan").password(password().encode("Kanch123@")).roles("USER").build();
       UserDetails admin= User.builder().username("admin").password(password().encode("admin")).roles("ADMIN").build();
return new InMemoryUserDetailsManager(kanchan,admin);
   }*/

   //thus mdthod comes after inmemory method as we are giving passwords there as a plain text so to encrypt it and semd for authentication we write here
   @Bean
    public static PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
