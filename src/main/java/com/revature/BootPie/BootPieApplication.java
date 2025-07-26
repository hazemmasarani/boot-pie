package com.revature.BootPie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.revature.BootPie.models.Pie;
import com.revature.BootPie.services.PieService;

@SpringBootApplication
public class BootPieApplication {

	@Value("${spring.application.name}")
	private String appName;

	public static void main(String[] args) {
		SpringApplication.run(BootPieApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
		return _ -> {
			System.out.println("BootPie Application has started successfully!\nApp Name is: " + appName);

			String[] beanNames = applicationContext.getBeanDefinitionNames();
			System.out.println("Beans provided by Spring Boot:");
			for (String beanName : beanNames) {
				if(beanName.contains("pie") || beanName.contains("inspector")) {
					System.out.println("Bean Name: " + beanName);
				}
				System.out.println(beanName);
			}
			System.out.println("Total number of beans: " + beanNames.length);
			System.out.println("Application Context ID: " + applicationContext.getId()); 
		};
	}

	@Bean
	public CommandLineRunner pieInspector(PieService pieService) {
		return _ -> {
			Pie randomPie = pieService.getRandonPie();
			if (randomPie != null) {
				System.out.println("Random Pie: " + randomPie);
			} else {
				System.out.println("No pies available.");
			}
		};
	}

}
