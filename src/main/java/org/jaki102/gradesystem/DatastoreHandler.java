package org.jaki102.gradesystem;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DatastoreHandler {
    private static DatastoreHandler Instance = new DatastoreHandler();
    private Datastore datastore;

    private DatastoreHandler() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.jaki102.gradesystem.model");
        datastore = morphia.createDatastore(new MongoClient("localhost", 8004), "GradeSystem");
        datastore.ensureIndexes();
    }

    public static DatastoreHandler getInstance() {
        return Instance;
    }

    public Datastore getDatastore() {
        return datastore;
    }

}
