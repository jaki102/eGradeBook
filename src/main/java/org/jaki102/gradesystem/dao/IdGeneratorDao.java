package org.jaki102.gradesystem.dao;

import org.jaki102.gradesystem.DatastoreHandler;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.jaki102.gradesystem.model.IdGenerator;

import java.util.List;

public class IdGeneratorDao {
    Datastore datastore = DatastoreHandler.getInstance().getDatastore();

    public int generateStudentIndex() {
        int id = 0;
        Query<IdGenerator> query = datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            create(new IdGenerator(0, 0, 0));
        } else {
            int newStudentId = query.get().getStudentId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("studentId", newStudentId);
            datastore.findAndModify(query, updateOperations);
            id = newStudentId;
        }
        return id;
    }

    public int generateCourseId() {
        int id = 0;
        Query<IdGenerator> query = datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            create(new IdGenerator(0, 0, 0));
        } else {
            int newCourseId = query.get().getCourseId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("courseId", newCourseId);

            datastore.findAndModify(query, updateOperations);
            id = newCourseId;
        }
        return id;
    }

    public int generateGradeId() {
        int id = 0;
        Query<IdGenerator> query = datastore.find(IdGenerator.class);
        if (query.countAll() == 0) {
            create(new IdGenerator(0, 0, 0));
        } else {
            int newGradeId = query.get().getGradeId() + 1;
            final UpdateOperations<IdGenerator> updateOperations = datastore.createUpdateOperations(IdGenerator.class)
                    .set("gradeId", newGradeId);
            datastore.findAndModify(query, updateOperations);
            id = newGradeId;
        }
        return id;
    }

    public IdGenerator read(Integer primaryKey) {
        // TODO - napisać logikę
        return null;
    }

    public boolean update(IdGenerator updateObject) {
        // TODO - uzupełnić
        return false;
    }

    public List<IdGenerator> getAll() {
        final Query<IdGenerator> query = datastore.createQuery(IdGenerator.class);
        final List<IdGenerator> ids = query.asList();
        return ids;
    }

    public IdGenerator create(IdGenerator object) {
        datastore.save(object);
        return object;
    }
}
