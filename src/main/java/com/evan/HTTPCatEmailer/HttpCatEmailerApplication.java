package com.evan.HTTPCatEmailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class HttpCatEmailerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(HttpCatEmailerApplication.class);
		application.run(args);
	}
}