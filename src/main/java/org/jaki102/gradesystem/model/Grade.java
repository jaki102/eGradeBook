package org.jaki102.gradesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Embedded
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity("grades")
public class Grade {
    //@InjectLinks({@InjectLink(value = "/students/{studentIndex}/grades/{id}", rel = "self"), @InjectLink(value = "/students/{studentIndex}/grades", rel = "parent")})
//    @XmlElement(name = "link")
//    @XmlElementWrapper(name = "links")
//    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
//    List<Link> links;

    @XmlTransient
    private int studentIndex;

/*    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId _id;*/

    private int id;
  //  private static long inc = 1;
    private float value;

  //  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CET")
    private Date date;

    @Embedded
    private Course course;

   // private static AtomicLong counter = new AtomicLong(100);
    private static int idNumber = 0;

    public Grade(){}

    public Grade(float value, Date date, Course course) {
        this.id = idNumber++;
        this.value = value;
        this.date = date;
        this.course = course;
    }

    public Grade(Grade grade) {
        this.studentIndex = grade.studentIndex;
        this.id = idNumber++;
        this.value = grade.getValue();
        this.date = grade.getDate();
        this.course = grade.getCourse();
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public int getId(){
        return this.id;
    }

    public float getValue() {
        return this.value;
    }

    public Date getDate() {
        return this.date;
    }

    public Course getCourse() { return this.course; }

/*    @XmlTransient
    public ObjectId get_id() {
        return _id;
    }*/
public static int getIdNumber() {
    return idNumber;
}

    public static void setIdNumber(int idNumber) {
        Grade.idNumber = idNumber;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

/*    public void set_id(ObjectId _id) {
        this._id = _id;
    }*/

/*    public void setId(){
        this.id = inc++;
    }*/

    public void setId(int id){
        this.id = id;
    }

    public void setValue(float value) {
        if(value>=3 && value <=5 || value ==2){
            if(value%0.5 == 0){
                this.value = value;
            }else{
                this.value = 2.0f;
            }
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "Grade{" + "id=" + id + ", studentIndex=" + studentIndex + ", value=" + value + ", date=" + date + ", course=" + "" + '}';
    }

}
