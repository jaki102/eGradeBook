package org.jaki102.gradesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Entity("students")
@XmlRootElement(name = "student")
public class Student {

    @Id
    @XmlTransient
    private ObjectId ID;

    @Indexed(name = "index", unique = true)
    private int index;

 //   private static long increment = 1;
    private String firstName;
    private String lastName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CET")
    private Date birthday;

    private List<Grade> grades;

  //  private static AtomicLong counter = new AtomicLong(100);
    @InjectLinks({
            @InjectLink(value = "/students/{index}", rel = "self"),
            @InjectLink(value = "/students", rel = "parent"),
            @InjectLink(value = "/students/{index}/grades", rel = "grades")
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @JsonProperty("links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> links;

    public Student(){
        this.grades = new ArrayList<>();
    }

    public Student(String firstName, String lastName, Date birthday, ArrayList<Grade> grades) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.grades = grades;
    }

    public Student(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.birthday = student.getBirthday();
        this.grades = student.getGrades();
    }

    public int getIndex(){
        return this.index;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getBirthday(){
        return this.birthday;
    }

    @XmlElement(name = "grade")
    @XmlElementWrapper(name = "grades")
    @JsonProperty("grades")
    public List<Grade> getGrades(){
        return this.grades;
    }

    @XmlTransient
    public ObjectId getID() {
        return ID;
    }

    public void setID(ObjectId ID) {
        this.ID = ID;
    }

    public void setIndex(int index){
        this.index = index;
    }

/*
    public void setIndex(){
        this.index = increment++;
    }
*/

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }

    public void setGrades(List<Grade> grades){
        this.grades = grades;
    }
    @Override
    public String toString() {
        return "Student{" + "index=" + index + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthday=" + birthday + ", grades=" + grades + '}';
    }

    public void addGrade(Grade grade) {
        getGrades().add(grade);
    }

    public Grade getGradeById(long id) {
        Optional<Grade> grade = getGrades().stream().filter(c -> c.getId() == id).findFirst();
        return grade.orElse(null);
    }

    public boolean updateStudentGrade(Grade grade) {
        int idx = getGrades().indexOf(getGradeById(grade.getId()));
        if (idx != -1) {
            getGrades().set(idx, grade);
            return true;
        } else
            return false;
    }

    public boolean removeStudentGradeById(long id) {
        System.out.println("Removing: " + id);
        return getGrades().remove(getGradeById(id));
    }
}