package fi.konstgjord.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"fi.konstgjord.first.config",
		"fi.konstgjord.first.controller",
		"fi.konstgjord.first.mapper",
		"fi.konstgjord.first.model",
		"fi.konstgjord.first.repository",
		"fi.konstgjord.first.service"
})
public class FirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}

}
