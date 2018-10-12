package com.nomiente.verticle;

import java.util.ArrayList;
import java.util.List;
import com.nomiente.model.Vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;

@Component
public class VerticleDemo extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(VerticleDemo.class);
	
	/*@Autowired
	public VerticleDemo(Vertx vertx) {
		this.vertx = vertx;
	}*/
	
	@Override
	public void start(Future<Void> future) throws Exception {
		Router router = Router.router(getVertx());
		router.get("/vehicle").handler(routingContext -> {
			String queryParams = routingContext.request().params().get("color");
			System.out.println("Params: " + queryParams);
			List<Vehicle> vehicles = new ArrayList<>();
			vehicles.add(new Vehicle("Pvto", "Azul", 2019));
			vehicles.add(new Vehicle("Umaña", "Amarillo pollito", 2000));
			vehicles.add(new Vehicle("Jimbo", "Rosa", 2009));
			
			JsonArray vehiclesJson = new JsonArray(vehicles); 
			
			routingContext.response().setStatusCode(200).end(vehiclesJson.encodePrettily());
		});
		
		HttpServerOptions options = new HttpServerOptions();
		options.setPort(8080);
		
		getVertx().createHttpServer(options).requestHandler(router::accept).listen(ar -> {
			if (ar.succeeded()) {
				future.complete();
			} else {
				ar.cause().printStackTrace();
				future.fail(ar.cause());
			}
		});
	}
	
}
