package com.nomiente.configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.core.HazelcastInstance;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

@Configuration
public class VertxConfiguration {

	private static final Logger log = LoggerFactory.getLogger(Vertx.class);
		
	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	private Vertx vertx;
	
	@Bean(destroyMethod = "")
	public Vertx vertx() {
		return this.vertx;
	}
	
	@PostConstruct
	public void init() throws UnknownHostException, InterruptedException, ExecutionException {
		VertxOptions options = new VertxOptions();
		options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		options.setClustered(true);
		options.setClusterManager(new HazelcastClusterManager(hazelcastInstance));
		options.setClusterHost(Inet4Address.getLocalHost().getHostAddress());
		CompletableFuture<Vertx> fut = new CompletableFuture<>();
		Vertx.clusteredVertx(options, ar -> {
			if (ar.succeeded()) {
				log.debug("Vertx instance created");
				fut.complete(ar.result());
			} else {
				log.error("Error :" + ar.cause());
				fut.completeExceptionally(ar.cause());
			}
		});
		
		vertx = fut.get();
	}
	
	@PreDestroy
	public void close() {
		Future<Void> closeFurute = Future.future();
		vertx.close();
		closeFurute.setHandler(ar -> {
			if (ar.failed()) {
				log.error(ar.cause().getMessage(), ar.cause());
			}
		});
	}
	
}
