package br.com.crud.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan({"br.com.crud.app.backend.model"})
@EnableJpaRepositories
@ComponentScan({"br.com.crud.app.backend"})
public class CrudAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudAppApiApplication.class, args);
	}
}
