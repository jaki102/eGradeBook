package org.jaki102.gradesystem.dao;

import org.jaki102.gradesystem.DatastoreHandler;
import org.jaki102.gradesystem.model.Course;
import org.jaki102.gradesystem.model.Student;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class CoursesDao {

    Datastore datastore = DatastoreHandler.getInstance().getDatastore();

    public Course getCourses(Integer id){
        return datastore.createQuery(Course.class).field("id").equal(id).get();

    }

    public boolean updateCourse(Course updateObject){
        final Query<Course> courseToUpdate = datastore.createQuery(Course.class).field("id").equal(updateObject.getId());
        final UpdateOperations<Course> updateOperations = datastore.createUpdateOperations(Course.class)
                .set("name", updateObject.getName())
                .set("lecturer", updateObject.getLecturer());
        datastore.update(courseToUpdate, updateOperations);
        return true;
    }

    public List<Course> getAll() {
        final Query<Course> query = datastore.createQuery(Course.class);
        final List<Course> courses = query.asList();
        return courses;
    }

    public Course readByParameters(String name, String lecturer) {
        return datastore.createQuery(Course.class).field("name").equal(name)
                .field("lecturer").equal(lecturer).get();
    }

    public Course readByParameters(Integer id) {
        return datastore.createQuery(Course.class).field("id").equal(id).get();
    }

    public List<Course> getByLecturer(String name, String lecturer) {
        final Query<Course> query = datastore.createQuery(Course.class);
        if (lecturer != null)
            query.field("lecturer").containsIgnoreCase(lecturer);
        if(name != null)
            query.field("name").containsIgnoreCase(name);
        List<Course> courses = query.asList();
        return courses;
    }

    public Course create(Course course) {
        datastore.save(course);
        return course;
    }

    public boolean delete(Integer id) {
        datastore.delete(getCourses(id));
        if (getCourses(id) == null)
            return true;
        else
            return false;
    }
}
