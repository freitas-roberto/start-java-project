package br.eti.freitas.startproject.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.eti.freitas.startproject.infrastructure.constant.SecurityConstant;
import br.eti.freitas.startproject.infrastructure.filter.SecurityRequestFilter;
import br.eti.freitas.startproject.infrastructure.handler.CustomAuthenticationEntryPoint;
import br.eti.freitas.startproject.infrastructure.handler.CustomAuthenticationFailureHandler;
import br.eti.freitas.startproject.infrastructure.service.impl.SecurityUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
	
	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPointJwt;

	@Autowired
	private SecurityUserDetailsServiceImpl customUserDetailsService;

	@Autowired
	private SecurityRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

    protected static final String[] ACTUATOR_WHITELIST = {
            "/actuator/**"
        };
    
	protected static final String[] AUTH_WHITELIST = {
	        "/v3/api-docs/**",
	        "/v3/api-docs.yaml",
	        "/swagger-ui/**",
			"/swagger-ui.html",
	        "/webjars/**",
            "/configuration/**",
	        "/swagger-resources/**"
	};


	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth  
									.antMatchers(AUTH_WHITELIST).permitAll()
		                            .antMatchers(ACTUATOR_WHITELIST).permitAll()
		                            .antMatchers(SecurityConstant.SECURITY_JWT_URI_AUTHENTICATE).permitAll()
		                            .antMatchers(SecurityConstant.SECURITY_JWT_URI_APPLICATION).authenticated()
		                            .anyRequest().authenticated())
				.cors().disable()
		        .csrf().disable()
	            .formLogin().disable()
	            .httpBasic().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPointJwt)
				.and()
				.authenticationProvider(authenticationProvider());

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
