package org.jaki102.gradesystem.Service;

import org.jaki102.gradesystem.dao.CoursesDao;
import org.jaki102.gradesystem.dao.DaoManager;
import org.jaki102.gradesystem.model.Course;

import java.util.List;

public class CourseService {

    public List<Course> getAllCourses() {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.getAll();
    }

    public Course addCourse(Course course) {
        IdGeneratorService generator = new IdGeneratorService();
        int newId = generator.generateCourseId();
        course.setId(newId);

        DaoManager manager = new DaoManager();
        course.setId(newId);
        CoursesDao courseDao = manager.getCoursesDao();
        courseDao.create(course);

        return course;
    }

    public Course getCourse(int id) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.getCourses(id);
    }

    public Course getCourseByParameters(String name, String lecturer) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.readByParameters(name, lecturer);
    }

    public Course getCourseById(int id) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.readByParameters(id);
    }

    public List<Course> getCoursesByLecturerFilter(String name, String lecturer) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.getByLecturer(name, lecturer);
    }

    public boolean updateCourse(Course course) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.updateCourse(course);
    }

    public boolean deleteCourse(int id) {
        DaoManager manager = new DaoManager();
        CoursesDao courseDao = manager.getCoursesDao();
        return courseDao.delete(id);
    }
}
