package com.valdir.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.valdir.helpdesk.security.JWTAuthenticationFilter;
import com.valdir.helpdesk.security.JWTUtil;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	//interface que representa o ambiente atual que o app está sendo executado
	@Autowired
	private Environment env; 
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * Desabilitar a proteção contra o ataque csrf
	 * Depois garantir que a sessão de usuário não vai ser criada
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
			
		}
		
		http.cors().and().csrf().disable();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEndcoder());
	}
	
	/*
	 * Liberar de forma explicíta as configurações de cross-origin
	 * Liberar as requisições para o meu Back-end
	 * Para receber requisições de múltiplas fontes
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	/*
	 * Adicionar a nossa senha criptografada ao BD 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEndcoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	
	
	
}
