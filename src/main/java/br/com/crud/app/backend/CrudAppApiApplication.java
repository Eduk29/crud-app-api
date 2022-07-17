package br.com.crud.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"br.com.crud.app.backend.model"})				// Scan das entitys da aplica��o (Models)
@EnableJpaRepositories
public class CrudAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudAppApiApplication.class, args);
	}

}
