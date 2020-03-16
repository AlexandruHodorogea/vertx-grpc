package com.housework.vertxrpc.client;

import com.housework.vertexrpc.GreeterGrpc;
import com.housework.vertexrpc.HelloRequest;
import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.grpc.VertxChannelBuilder;
import org.springframework.stereotype.Component;

import static com.housework.vertexrpc.GreeterGrpc.newVertxStub;

@Component
public class Client {
    Logger logger = LoggerFactory.getLogger(Client.class);

    Vertx vertx = Vertx.vertx();

    ManagedChannel channel = VertxChannelBuilder
            .forAddress(vertx, "localhost", 8080)
            .usePlaintext(true)
            .build();

    // Get a stub to use for interacting with the remote service
    GreeterGrpc.GreeterVertxStub stub = newVertxStub(channel);

    public void sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        // Call the remote service
        this.stub.sayHello(request, ar -> {
            if (ar.succeeded()) {
                logger.info("Got the server response: " + ar.result().getMessage());
            } else {
                logger.info("Coult not reach server " + ar.cause().getMessage());
            }
        });
    }
}
