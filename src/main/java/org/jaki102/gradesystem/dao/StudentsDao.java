package org.jaki102.gradesystem.dao;

import org.jaki102.gradesystem.DatastoreHandler;
import org.jaki102.gradesystem.model.Student;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class StudentsDao {
    Datastore datastore = DatastoreHandler.getInstance().getDatastore();

    public Student getStudents(Integer index){
        return datastore.createQuery(Student.class).field("index").equal(index).get();
    }

    public boolean updateStudent(Student updateObject){
        final Query<Student> studentToUpdate = datastore.createQuery(Student.class).field("index").equal(updateObject.getIndex());
        final UpdateOperations<Student> updateOperations = datastore.createUpdateOperations(Student.class)
                .set("firstName", updateObject.getFirstName())
                .set("lastName", updateObject.getLastName())
                .set("birthday", updateObject.getBirthday());
        if(updateObject.getGrades() != null && !updateObject.getGrades().isEmpty())
            updateOperations.set("grades", updateObject.getGrades());
        datastore.update(studentToUpdate, updateOperations);
        // TODO - check if succeeded
        return true;
    }

    public List<Student> getAll() {
        final Query<Student> query = datastore.createQuery(Student.class);
        final List<Student> studentsList = query.asList();
        return studentsList;
    }

    public boolean updateStudent(Student updateObject, boolean force) {
        final Query<Student> studentToUpdate = datastore.createQuery(Student.class).field("index").equal(updateObject.getIndex());
        final UpdateOperations<Student> updateOperations = datastore.createUpdateOperations(Student.class)
                .set("firstName", updateObject.getFirstName())
                .set("lastName", updateObject.getLastName())
                .set("birthday", updateObject.getBirthday());
        if(force == true)
            updateOperations.set("grades", updateObject.getGrades());
        datastore.update(studentToUpdate, updateOperations);
        // TODO - check if succeeded
        return true;
    }

    public Student create(Student student) {
        datastore.save(student);
        return student;
    }

    public boolean delete(Integer index) {
        datastore.delete(getStudents(index));
        if (getStudents(index) == null)
            return true;
        else
            return false;
    }

}
