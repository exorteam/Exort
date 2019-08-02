package exort.apiserver.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import exort.apiserver.filter.JwtFilter;

@Configuration
public class JwtConfig {

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/users/*");
		registrationBean.addUrlPatterns("/test/auth");
		registrationBean.addUrlPatterns("/permission/*");
		registrationBean.addUrlPatterns("/activities/*");
		registrationBean.addUrlPatterns("/associations/*");
		registrationBean.addUrlPatterns("/articles/*");

		return registrationBean;
	}
}

