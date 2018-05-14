package org.jaki102.gradesystem.dao;

public class DaoManager {

    public StudentsDao getStudentsDao() {
        return new StudentsDao();
    }

    public CoursesDao getCoursesDao() {
        return new CoursesDao();
    }

    public IdGeneratorDao getIdGeneratorDao() {
        return new IdGeneratorDao();
    }
}
