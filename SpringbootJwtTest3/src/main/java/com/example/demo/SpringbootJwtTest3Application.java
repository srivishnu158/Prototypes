package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.controller.Controller1;

//@Import({Controller1.class})
@ComponentScan(basePackages = {"com.controller","com.service","com.example.demo","com.config"})
@EnableJpaRepositories("com.repository")
@EntityScan("com.entity")
@SpringBootApplication
public class SpringbootJwtTest3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJwtTest3Application.class, args);
	}

}
