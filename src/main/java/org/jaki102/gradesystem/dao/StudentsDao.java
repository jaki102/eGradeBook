package org.jaki102.gradesystem.dao;

import org.jaki102.gradesystem.DatastoreHandler;
import org.jaki102.gradesystem.model.Student;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Date;
import java.util.List;

public class StudentsDao {
    Datastore datastore = DatastoreHandler.getInstance().getDatastore();

    public Student getStudents(int index){
        return datastore.createQuery(Student.class).field("index").equal(index).get();
    }

    public boolean updateStudent(Student updateObject){
        final Query<Student> studentToUpdate = datastore.createQuery(Student.class).field("index").equal(updateObject.getIndex());
        final UpdateOperations<Student> updateOperations = datastore.createUpdateOperations(Student.class)
                .set("index", updateObject.getIndex())
                .set("firstName", updateObject.getFirstName())
                .set("lastName", updateObject.getLastName())
                .set("birthday", updateObject.getBirthday());
        if(updateObject.getGrades() != null && !updateObject.getGrades().isEmpty())
            updateOperations.set("grades", updateObject.getGrades());
        datastore.update(studentToUpdate, updateOperations);
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
                .set("index", updateObject.getIndex())
                .set("firstName", updateObject.getFirstName())
                .set("lastName", updateObject.getLastName())
                .set("birthday", updateObject.getBirthday());
        if(force == true)
            updateOperations.set("grades", updateObject.getGrades());
        datastore.update(studentToUpdate, updateOperations);
        return true;
    }
    public List<Student> getStudentsByFilters(String firstName, String lastName, Date birthday, String dateRelation) {
        final Query<Student> query = datastore.createQuery(Student.class);
        if(firstName != null)
            query.field("firstName").containsIgnoreCase(firstName);
        if(lastName != null)
            query.field("lastName").containsIgnoreCase(lastName);
        if(birthday != null && dateRelation != null) {
            if(dateRelation.equals("equal"))
                query.field("birthday").equal(birthday);
            else if(dateRelation.equals("grater"))
                query.field("birthday").greaterThan(birthday);
            else if(dateRelation.equals("lower"))
                query.field("birthday").lessThan(birthday);
        }
        List<Student> filteredStudents = query.asList();
        return filteredStudents;
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