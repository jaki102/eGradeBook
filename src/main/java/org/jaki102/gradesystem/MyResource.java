package org.jaki102.gradesystem;

import jersey.repackaged.com.google.common.collect.Lists;
import org.jaki102.gradesystem.Service.CourseService;
import org.jaki102.gradesystem.Service.IdGeneratorService;
import org.jaki102.gradesystem.Service.StudentsService;
import org.jaki102.gradesystem.dao.StudentsDao;
import org.jaki102.gradesystem.model.Course;
import org.jaki102.gradesystem.model.Grade;
import org.jaki102.gradesystem.model.Student;
//import org.jaki102.gradesystem.model.mockModel;
import org.mongodb.morphia.query.Query;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import static org.jaki102.gradesystem.model.mockModel.courses;
//import static org.jaki102.gradesystem.model.mockModel.getCoursesList;

@Path("/")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MyResource {

  //  private final List<Student> studentsList = mockModel.getStudentsList();

    @GET
    @Path("/students")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllStudents( @QueryParam("index") Integer index, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("birthday") String birthday, @QueryParam("dateRelation") String dateRelation) {
        StudentsService studentService = new StudentsService();
        List<Student> students = null;
        try {
            students = studentService.getStudentsByFilters(index, firstName, lastName, birthday, dateRelation);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(Lists.newArrayList(students)) {
        };

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/students/{index}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentByIndex(@PathParam("index") int index) {
        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedStudent).build();
    }

    @POST
    @Path("/students")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudents(Student student) {
        if (student != null) {
            // checking if grade's course exists
            CourseService courseService = new CourseService();
            Course searchedCourse;

            for (Grade grade : student.getGrades()) {
                searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());
                if (searchedCourse == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();
                }
            }

            StudentsService studentService = new StudentsService();
            studentService.addStudent(student);

            String result = "Student " + student + " added!\n";

            // creating response
            Response response = Response.status(Response.Status.CREATED).header("Location", "/students/" + student.getIndex()).entity(result).build();
            return response;
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Student cannot be null!").build();
    }

    @PUT
    @Path("/students/{index}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateStudent(Student student, @PathParam("index") int index) {

        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // checking if grade's course exists
        CourseService courseService = new CourseService();
        Course searchedCourse;

        for (Grade grade : student.getGrades()) {
            searchedCourse = courseService.getCourseByParameters(grade.getCourse().getName(), grade.getCourse().getLecturer());
            if (searchedCourse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();
            }
        }

        student.setIndex(index);
        // updating student
        boolean status = studentService.updateStudent(student, false);
        String result = "Student " + student + " updated!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("/students/{index}")
    public Response deleteStudent(@PathParam("index") int index) {
        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        // updating student
        boolean status = studentService.deleteStudent(index);
        if (status == true) {
            String result = "Student " + searchedStudent + " deleted!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not deleted").build();
    }

    @GET
    @Path("/students/{index}/grades")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrades(@PathParam("index") int index,
                                     @QueryParam("courseName") String courseName,
                                     @QueryParam("date") Date date,
                                     @QueryParam("value") String value,
                                     @QueryParam("valueRelation") String valueRelation) {
        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

        List<Grade> grades = searchedStudent.getGrades();
        if (grades == null || grades.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("Student's grades not found").build();

        if (date != null) {
            grades = grades.stream().filter(gr -> gr.getDate().equals(date)).collect(Collectors.toList());
        }
        // filtering by course
        if (courseName != null) {
            grades = grades.stream().filter(gr -> gr.getCourse().getName().contains(courseName)).collect(Collectors.toList());
        }

        // filtering by grade's value
        if (value != null /*&& valueRelation != null*/) {
//            switch (valueRelation.toLowerCase()) {
//                case "greater":
//                    grades = grades.stream().filter(gr -> gr.getValue() > Float.valueOf(value).floatValue()).collect(Collectors.toList());
//                    break;
//                case "lower":
//                    grades = grades.stream().filter(gr -> gr.getValue() < Float.valueOf(value).floatValue()).collect(Collectors.toList());
//                    break;
            grades = grades.stream().filter(gr -> gr.getValue() == Float.valueOf(value).floatValue()).collect(Collectors.toList());
           // }
        }
        // creating list of student's grades
        GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(Lists.newArrayList(grades)) {};
        // creating xml response
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/students/{index}/grades/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getStudentGrade(@PathParam("index") int index, @PathParam("id") int id) {
        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }

        // getting student's grade by it's id
        Grade searchedGrade = searchedStudent.getGradeById(id);

        // checking if student's grade exists
        if (searchedGrade == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
        }

        // creating xml response
        return Response.status(Response.Status.OK).entity(searchedGrade).build();
    }

    @POST
    @Path("/students/{index}/grades")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addStudentGrades(@PathParam("index") int index, Grade grade) {
        // checking if grade is a null
        if (grade != null) {
            // getting student by it's index
            StudentsService studentService = new StudentsService();
            Student searchedStudent = studentService.getStudent(index);

            // checking if student exists
            if (searchedStudent == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

            CourseService courseService = new CourseService();
            Course searchedCourse = courseService.getCourseById(grade.getCourse().getId());

            if (searchedCourse == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();

            IdGeneratorService generator = new IdGeneratorService();
            grade.setId(generator.generateGradeId());
            grade.setStudentIndex(searchedStudent.getIndex());
            grade.setCourse(searchedCourse);
            searchedStudent.addGrade(grade);
            studentService.updateStudent(searchedStudent, false);
            String result = "Student grade " + grade + " added!\n";

            // creating response
            return Response.status(Response.Status.CREATED).header("Location", "students/" + searchedStudent.getIndex() + "/grades/" + grade.getId()).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Grade cannot be null!").build();
    }

    @PUT
    @Path("/students/{index}/grades/{id}")

    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Response updateStudentGrade(Grade grade, @PathParam("index") int index, @PathParam("id") int id) {

        // checking if grade is a null
        if (grade != null) {
            // getting student by it's index
            StudentsService studentService = new StudentsService();
            Student searchedStudent = studentService.getStudent(index);

            // checking if student exists
            if (searchedStudent == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
            }

            // getting student's grade by it's id
            Grade searchedGrade = searchedStudent.getGradeById(id);

            // checking if student's grade exists
            if (searchedGrade == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();
            }

            // checking if grade's course exists
            CourseService courseService = new CourseService();
            Course searchedCourse = courseService.getCourseById(grade.getCourse().getId());
            System.out.println("Course : " + searchedCourse);
            if (searchedCourse == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Grade's course not found").build();

            searchedCourse.setId(grade.getCourse().getId());
            grade.setId(id);
            grade.setStudentIndex(searchedStudent.getIndex());
            grade.setCourse(searchedCourse);
            searchedStudent.updateStudentGrade(grade);
            studentService.updateStudent(searchedStudent, false);
            String result = "Student grade " + grade + " updated!";

            // creating response
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).entity("Grade cannot be null!").build();
    }

    @DELETE
    @Path("/students/{index}/grades/{id}")
    public Response deleteStudentGrade(@PathParam("index") int index, @PathParam("id") int id) {
        // getting student by it's index
        StudentsService studentService = new StudentsService();
        Student searchedStudent = studentService.getStudent(index);

        // checking if student exists
        if (searchedStudent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        Grade searchedGrade = searchedStudent.getGradeById(id);
        // checking if grade exists
        if (searchedGrade == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Grade not found").build();

        // removing student grade
        searchedStudent.removeStudentGradeById(id);
        studentService.updateStudent(searchedStudent, false);
        String result = "Student grade " + searchedGrade + " deleted!";

        // creating response
        return Response.status(Response.Status.OK).entity(result).build();
    }
    @GET
    @Path("/courses")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllCourses(@QueryParam("lecturer") String lecturer, @QueryParam("name") String name) {
        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getCoursesByLecturerFilter(name, lecturer);

        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(Lists.newArrayList(courses)) {
        };
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/courses/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCourseById(@PathParam("id") int id) {
        // getting course by id
        CourseService courseService = new CourseService();
        Course course = courseService.getCourse(id);

        // checking if course exists
        if (course == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        // creating xml response
        return Response.status(Response.Status.OK).entity(course).build();
    }

    @POST
    @Path("/courses")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addCourse(Course course) {
        if (course != null) {
            CourseService courseService = new CourseService();
            course = courseService.addCourse(course);

            String result = "Course " + course + " added!\n";

            // creating response
            return Response.status(Response.Status.CREATED).header("Location", "/courses/" + course.getId()).entity(result).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).entity("Courses cannot be null!").build();
    }

    @PUT
    @Path("/courses/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCourse(Course course, @PathParam("id") int id) {

        CourseService courseService = new CourseService();
        Course searchedCourse = courseService.getCourse(id);

        // checking if course exists
        if (searchedCourse == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();

        course.setId(id);
        // updating course
        boolean status = courseService.updateCourse(course);
        if (status == true) {
            String result = "Course " + course + " updated!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not updated").build();
    }

    @DELETE
    @Path("/courses/{id}")
    public Response deleteCourse(@PathParam("id") int id) {
        // getting course by it's index
        CourseService courseService = new CourseService();
        Course searchedCourse = courseService.getCourse(id);

        // checking if course exists
        if (searchedCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        // updating course
        StudentsService studentService = new StudentsService();
        List<Student> students = studentService.getAllStudents();

        // TODO - Zrobić na streamach ładniej lub zapytanie do każdej oceny na query!
        // Deleting grades with deleting course
        if (students != null && !students.isEmpty()) {
//            List<Student> filteredStudent = new ArrayList<>();
            for (Student st : students) {
                for (int i = 0; i < st.getGrades().size(); i++) {//Grade gr: st.getGrades()) {
                    if (st.getGrades().get(i).getCourse().getId() == id) {
//                        filteredStudent.add(st);
                        System.out.println(st.getGrades().get(i));
                        st.removeStudentGradeById(st.getGrades().get(i).getId());
                    }
                }
                studentService.updateStudent(st, true);
            }
        }

        boolean status = courseService.deleteCourse(id);
        if (status == true) {
            String result = "Course " + searchedCourse + " deleted!";

            // creating response
            return Response.status(Response.Status.OK).entity(result).build();
        } else
            return Response.status(Response.Status.CONFLICT).entity("Error, not deleted").build();
    }
   }