package org.jaki102.gradesystem;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;
import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and provindexers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig()
                .packages("org.jaki102.gradesystem")
                .packages("org.glassfish.jersey.examples.linking")
                .register(DeclarativeLinkingFeature.class)
                .register(DateParamConverterProvider.class)
                .register(RestError.class)
                .register(CustomHeaders.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}