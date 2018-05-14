package org.jaki102.gradesystem.Service;

import org.jaki102.gradesystem.dao.DaoManager;
import org.jaki102.gradesystem.dao.StudentsDao;
import org.jaki102.gradesystem.model.Student;

import java.util.List;

public class StudentsService {

    public List<Student> getAllStudents() {
        DaoManager manager = new DaoManager();
        StudentsDao studentsDao = manager.getStudentsDao();
        return studentsDao.getAll();
    }

    public Student addStudent(Student student) {
        IdGeneratorService generator = new IdGeneratorService();
        int newId = generator.generateStudentIndex();
        student.setIndex(newId);

        DaoManager manager = new DaoManager();
        StudentsDao studentsDao = manager.getStudentsDao();
        studentsDao.create(student);

        return student;
    }

    public Student getStudent(int index) {
        DaoManager manager = new DaoManager();
        StudentsDao studentsDao = manager.getStudentsDao();
        return studentsDao.getStudents(index);
    }

    public boolean updateStudent(Student student, boolean force) {
        DaoManager manager = new DaoManager();
        StudentsDao studentsDao = manager.getStudentsDao();
        if (force == false)
            return studentsDao.updateStudent(student);
        else
            return studentsDao.updateStudent(student, true);
    }

    public boolean deleteStudent(int index) {
        DaoManager manager = new DaoManager();
        StudentsDao studentsDao = manager.getStudentsDao();
        return studentsDao.delete(index);
    }

}
