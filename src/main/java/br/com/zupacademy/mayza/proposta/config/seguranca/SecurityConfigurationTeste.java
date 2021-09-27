package br.com.zupacademy.mayza.proposta.config.seguranca;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Profile(("test"))
public class SecurityConfigurationTeste extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/**");;
    }
}

