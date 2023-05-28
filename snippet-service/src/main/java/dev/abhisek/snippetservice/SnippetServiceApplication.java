package dev.abhisek.snippetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SnippetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnippetServiceApplication.class, args);
	}

}
