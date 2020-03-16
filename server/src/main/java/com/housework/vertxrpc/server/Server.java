package com.housework.vertxrpc.server;

import com.housework.vertexrpc.GreeterGrpc;
import com.housework.vertexrpc.HelloReply;
import com.housework.vertexrpc.HelloRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import org.springframework.stereotype.Component;

@Component
public class Server extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(Server.class);

    GreeterGrpc.GreeterVertxImplBase greeterService = new GreeterGrpc.GreeterVertxImplBase() {
        @Override
        public void sayHello(HelloRequest request, Promise<HelloReply> future) {
            logger.info("Request for: " + request.getName());
            future.complete(HelloReply.newBuilder().setMessage("HI, " + request.getName() + "!").build());
        }
    };

    @Override
    public void start() throws Exception {
        VertxServer rpcServer = VertxServerBuilder
                .forAddress(vertx, "localhost", 8080)
                .addService(greeterService)
                .build();

        rpcServer.start(ar -> {
            if (ar.succeeded()) {
                logger.info("gRPC service started");
            } else {
                logger.info("Could not start server " + ar.cause().getMessage());
            }
        });
    }
}
