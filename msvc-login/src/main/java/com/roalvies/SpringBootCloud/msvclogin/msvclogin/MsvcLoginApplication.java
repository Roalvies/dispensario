package com.roalvies.SpringBootCloud.msvclogin.msvclogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//HABILITAMOS EL CONTEXTO FEIGN
@EnableFeignClients
@SpringBootApplication
public class MsvcLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcLoginApplication.class, args);
	}

}
