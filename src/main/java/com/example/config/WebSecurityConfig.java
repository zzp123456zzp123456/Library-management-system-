package com.example.config;

import com.example.entity.AuthUser;
import com.example.mapper.UserMapper;
import com.example.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserAuthService service;
    //用于处理登录
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里使用SpringSecurity提供的BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//        auth
//                .inMemoryAuthentication() //直接验证方式
//                .passwordEncoder(encoder) //密码加密器
//                .withUser("zzp")   //用户名
//                .password(encoder.encode("123456"))   //这里需要填写加密后的密码
//                .roles("user");   //用户的角色
        auth
                .userDetailsService(service)   //使用自定义的Service实现类进行验证
                .passwordEncoder(new BCryptPasswordEncoder());   //依然使用BCryptPasswordEncoder
    }

    @Resource
    UserMapper mapper;

    @Resource
    PersistentTokenRepository repository;

    @Bean
    public PersistentTokenRepository persistentRememberMeToken(@Autowired DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();

        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**","/auth/**").permitAll()    //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .antMatchers("/user/**","/api/user/**").hasRole("user")
                .antMatchers("/admin/**","/api/admin/**").hasRole("admin")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/api/doLogin")
                .successHandler(this::onAuthenticationSuccess)
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(60*60*24*2)
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/auth/login")
                .and()
                .csrf().disable();

    }
    private void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        AuthUser user = mapper.getPasswordByUsername(authentication.getName());
        session.setAttribute("user", user);
        if (user.getRole().equals("admin")) {
            httpServletResponse.sendRedirect("/mvc/admin/index");
        } else {
            httpServletResponse.sendRedirect("/mvc/user/index");
        }

    }
}
