package br.com.zupacademy.mayza.proposta.config.seguranca;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_propostas")
                .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_propostas")
                .antMatchers(HttpMethod.POST,"/cartoes/**").hasAuthority("SCOPE_cartoes")
                .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_actuator")
                .anyRequest().authenticated()).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).csrf().disable()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}

