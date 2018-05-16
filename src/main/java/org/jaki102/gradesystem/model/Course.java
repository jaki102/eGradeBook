package org.jaki102.gradesystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.*;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Entity("courses")
@XmlRootElement
@Embedded
public class Course {

    @Id
    @XmlTransient
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId _id;

    @Indexed
    private int id;

 //   private static long inc = 1;
    private String name;
    private String lecturer;
  //  private static AtomicLong counter = new AtomicLong(100);
    @InjectLinks({
            @InjectLink(value = "/courses/{id}", rel = "self"),
            @InjectLink(value = "/courses", rel = "parent")
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @JsonProperty("links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> links;

    public Course(){}

    public Course(String name, String lecturer) {
        this.name = name;
        this.lecturer = lecturer;
    }

    public Course(Course course) {
        this.name = course.getName();
        this.lecturer = course.getLecturer();
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    @XmlTransient
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
/*    public void setId(){
        this.id = this.inc++;
    }*/
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name='" + name + '\'' + ", lecturer='" + lecturer + '\'' + '}';
    }
}
