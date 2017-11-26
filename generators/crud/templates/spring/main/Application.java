package com.citigroup.nga.crud.moneymovement.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({
	"com.citigroup.nga.crud",
	"com.citigroup.nga.crudcore",
	"com.citigroup.nga.utilitiescore",
	"com.banamex.nga.gemfire",
	"com.banamex.nga.ebanking"
	})
//TODO Habilitar esta linea cuando se pase a Banamex
//@EnableScheduling
public class MoneyMovementCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyMovementCrudApplication.class, args);
	}
}
