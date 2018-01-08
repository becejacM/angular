
package org.levi9.security;

import org.levi9.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() throws BadCredentialsException {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequnce) {
				return charSequnce.toString();
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) throws BadCredentialsException {
				if (charSequence.equals(s)) {
					return true;
				} else {
					throw new BadCredentialsException("Wrong Password");
				}
			}
		};
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Bean
	public SecurityService securityService() {
		return this.securityService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.antMatcher("/**").authorizeRequests().antMatchers(HttpMethod.GET, "/api").permitAll()
				.antMatchers("/swagger-ui.html").permitAll().antMatchers("/auth/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/comments/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.DELETE, "/api/comments/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/components/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/api/components/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/components/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/components/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/components/all/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/components/search/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/configurations/all/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/api/configurations/get_my_configurations/**").hasAuthority("USER")
				.antMatchers(HttpMethod.POST, "/api/configurations/**").hasAuthority("USER")
				.antMatchers(HttpMethod.DELETE, "/api/configurations/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.PUT, "/api/configurations/").hasAuthority("USER")
				.antMatchers(HttpMethod.PUT, "/api/configurations/check_out/**").hasAuthority("USER")
				.antMatchers(HttpMethod.PUT, "/api/configurations/pay/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/configurations/deliver/**").hasAuthority("ADMIN")
				.antMatchers("/api/manufacturers/**").permitAll().antMatchers("/api/users/register/**").permitAll()
				.antMatchers("/api/users/confirm/**").permitAll().antMatchers("/api/users/**")
				.hasAnyAuthority("ADMIN", "USER").antMatchers(HttpMethod.GET, "/api/types/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/types/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/types/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/types/**").hasAuthority("ADMIN").anyRequest().permitAll();
		// Custom JWT based authentication
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

}
