package com.lm.SpringSecLogin;

import org.springframework.boot.SpringApplication;								// Import of Spring Application, to run the application
import org.springframework.boot.autoconfigure.SpringBootApplication;			// Import of Spring Boot Application, to configure the application as a Spring Boot Application
import org.springframework.context.annotation.EnableAspectJAutoProxy;			// Import of AspectJ Auto Proxy, to enable the AspectJ Auto Proxy (a aspect-oriented programming framework)
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;	// Import of JPA Repositories, to enable the JPA Repositories (Java Persistence API)

/**
 * SpringSecLoginApplication class, to run the application
 *
 * @SpringBootApplication annotation, to configure the application as a Spring Boot Application
 * @EnableJpaRepositories annotation, to enable the JPA Repositories (Java Persistence API)
 * @EnableAspectJAutoProxy annotation, to enable the AspectJ Auto Proxy (a aspect-oriented programming framework)
 *
 */

@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class SpringSecLoginApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(SpringSecLoginApplication.class, args);
	}

}
