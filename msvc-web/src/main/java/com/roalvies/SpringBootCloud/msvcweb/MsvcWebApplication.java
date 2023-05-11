package com.roalvies.SpringBootCloud.msvcweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//HABILITAMOS EL CONTEXTO FEIGN
@EnableFeignClients
@SpringBootApplication
public class MsvcWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcWebApplication.class, args);
	}

}
