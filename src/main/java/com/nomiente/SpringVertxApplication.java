package com.nomiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.nomiente.verticle.VerticleDemo;

import io.vertx.core.Vertx;

@SpringBootApplication
public class SpringVertxApplication {

	@Autowired
	private Vertx vertx;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringVertxApplication.class, args);
	}
	
	@EventListener
	public void deployVerticles(ApplicationReadyEvent evt) {
		vertx.deployVerticle(VerticleDemo.class.getName());
	}
	
}
