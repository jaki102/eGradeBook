/*package org.jaki102.gradesystem.model;

import org.jaki102.gradesystem.DatastoreHandler;
import org.mongodb.morphia.Datastore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mockModel {

        private static final List<Student> studentsList = new ArrayList<>();
        public static final List<Course> courses = new ArrayList<>();

        static {

            Datastore datastore = DatastoreHandler.getInstance().getDatastore();


            List<Grade> gradeStudent = new ArrayList<>();

            Course course1 = new Course();
            course1.setId();
            course1.setName("TP-AL");
            course1.setLecturer("dr inż. Piotr Zieleniewicz");
            courses.add(course1);

            Course course2 = new Course();
            course2.setId();
            course2.setName("TP-SI");
            course2.setLecturer("dr inż. Tomasz Pawlak");
            courses.add(course2);

            Course course3 = new Course();
            course3.setId();
            course3.setName("Przedsiębiorstwo");
            course3.setLecturer("dr hab inż. Andrzej Jaszkiewicz");
            courses.add(course3);

            Course course4 = new Course();
            course4.setId();
            course4.setName("ZWiWO");
            course4.setLecturer("dr inż. Marcin Szeląg");
            courses.add(course4);

            Course course5 = new Course();
            course5.setId();
            course5.setName("AMiM");
            course5.setLecturer("dr inż. Bartłomiej Prędki");
            courses.add(course5);

            Course course6 = new Course();
            course6.setId();
            course6.setName("MiASI");
            course6.setLecturer("dr inż. Bartosz Walter");
            courses.add(course6);


            Grade grade1 = new Grade();
            grade1.setId();
            grade1.setValue(5);
            grade1.setDate(new Date("2018/5/20"));
            grade1.setCourse(courses.get(0));
            gradeStudent.add(grade1);

            Grade grade2 = new Grade();
            grade2.setId();
            grade2.setValue(3);
            grade2.setDate(new Date("2018/6/18"));
            grade2.setCourse(courses.get(1));
            gradeStudent.add(grade2);

            Student student1 = new Student();
            student1.setIndex();
            student1.setFirstName("Jakub");
            student1.setLastName("Białek");
            student1.setBirthday(new Date("1995/5/17"));
            student1.setGrades(gradeStudent);

            studentsList.add(student1);
            gradeStudent = new ArrayList();


            Grade grade3 = new Grade();
            grade3.setId();
            grade3.setValue(5);
            grade3.setDate(new Date("2018/5/20"));
            grade3.setCourse(courses.get(2));
            gradeStudent.add(grade3);

            Grade grade4 = new Grade();
            grade4.setId();
            grade4.setValue(4);
            grade4.setDate(new Date("2018/6/18"));
            grade4.setCourse(courses.get(3));
            gradeStudent.add(grade4);

            Student student2 = new Student();
            student2.setIndex();
            student2.setFirstName("Andrzej");
            student2.setLastName("Nowak");
            student2.setBirthday(new Date("1999/9/10"));
            student2.setGrades(gradeStudent);

            studentsList.add(student2);
            gradeStudent = new ArrayList();


            Grade grade5 = new Grade();
            grade5.setId();
            grade5.setValue(2);
            grade5.setDate(new Date("2018/5/20"));
            grade5.setCourse(courses.get(4));
            gradeStudent.add(grade5);

            Grade grade6 = new Grade();
            grade6.setId();
            grade6.setValue(2);
            grade6.setDate(new Date("2018/6/18"));
            grade6.setCourse(courses.get(5));
            gradeStudent.add(grade6);

            Student student3 = new Student();
            student3.setIndex();
            student3.setFirstName("Robert");
            student3.setLastName("Kubica");
            student3.setBirthday(new Date("1986/3/27"));
            student3.setGrades(gradeStudent);

            studentsList.add(student3);


            datastore.save(studentsList);
            datastore.save(courses);

        }

        private mockModel(){}

    public static List<Student> getStudentsList() {
        return studentsList;
    }

    public static List<Course> getCoursesList() {
        return courses;
    }

}*/

