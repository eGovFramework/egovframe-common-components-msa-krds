package egovframework.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"egovframework.com","org.egovframe.boot"})
public class EgovCmmnCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgovCmmnCodeApplication.class, args);
	}

}
