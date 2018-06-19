"use strict"

var url = 'http://localhost:8080/myapp/';

class Student {
    constructor(data = {firstName:"", lastName:"", birthday:""}, sub = true){
        this.firstName = new ko.observable(data.firstName);
        this.lastName = new ko.observable(data.lastName);
        this.birthday = new ko.observable(data.birthday);
        this.index = new ko.observable(data.index);
        if(sub) {
            this.addSubscribe();
        }
    }

    addSubscribe(){
        this.firstName.subscribe(this.PUT.bind(this));
        this.lastName.subscribe(this.PUT.bind(this));
        this.birthday.subscribe(this.PUT.bind(this));
    }

    getData(){
        return ko.toJSON({
            firstName: this.firstName,
            lastName: this.lastName,
            birthday: this.birthday
        });
    }

    PUT(){
        console.log(this.getData());
        $.ajax({
            url: "http://localhost:8080/myapp/students/" + ko.toJS(this.index),
            method: "PUT",
            data: this.getData(),
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        })
    }
    DELETE(){
        $.ajax({
            url: "http://localhost:8080/myapp/students/" + ko.toJS(this.index),
            method: "DELETE",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }
    POST(){
        $.ajax({
            url: "http://localhost:8080/myapp/students/",
            method: "POST",
            async: false,
            data: this.getData(),
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }
}
class Course {
    constructor(data = {name: "", lecturer: "", id: ""}){
        this.name = new ko.observable(data.name);
        this.lecturer = new ko.observable(data.lecturer);
        this.id = new ko.observable(data.id);
        this.addSubscribe();
    }

    addSubscribe(){
        this.name.subscribe(this.PUT.bind(this));
        this.lecturer.subscribe(this.PUT.bind(this));
        this.id.subscribe(this.PUT.bind(this));;
    }

    getData(){
        return ko.toJSON({
            name: this.name,
            lecturer: this.lecturer
        });
    }

    PUT(){
        $.ajax({
            url: "http://localhost:8080/myapp/courses/" + ko.toJS(this.id),
            method: "PUT",
            data: this.getData(),
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }

    DELETE(){
        $.ajax({
            url: "http://localhost:8080/myapp/courses/" + ko.toJS(this.id),
            method: "DELETE",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }

    POST(){
        $.ajax({
            url: "http://localhost:8080/myapp/courses/",
            method: "POST",
            async: false,
            data: this.getData(),
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }
}
class Grade {
    constructor(data = {id: "", value: "", course: {id:""}, date: ""},student = 0){
        this.student = student;
        this.id = new ko.observable(data.id);
        this.value = new ko.observable(data.value);
        this.course = new ko.observable(data.course.id);
        this.date = new ko.observable(data.date);
        this.addSubscribe();
    }

    addSubscribe(){
        this.id.subscribe(this.PUT.bind(this));
        this.value.subscribe(this.PUT.bind(this));
        this.course.subscribe(this.PUT.bind(this));
        this.date.subscribe(this.PUT.bind(this));
    }

    getData(){
        return ko.toJSON({
            value: this.value,
            course: {id: this.course},
            date: this.date
        });
    }

    PUT(){
        $.ajax({
            url: "http://localhost:8080/myapp/students/" + this.student + "/grades/" + ko.toJS(this.id),
            method: "PUT",
            data: this.getData(),
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }

    DELETE(){
        $.ajax({
            url: "http://localhost:8080/myapp/students/" + this.student + "/grades/" + ko.toJS(this.id),
            method: "DELETE",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }

    POST(){
        $.ajax({
            url: "http://localhost:8080/myapp/students/" + this.student + "/grades/",
            method: "POST",
            async: false,
            data: this.getData(),
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            }
        });
    }
}
class StudentSearch extends Student{
    constructor(observableArray ,data = {index: "", firstName: "", lastName: "", birthday: ""}, sub = true){
        super(data,sub);
        this.studentsObservableArray = observableArray;
    }

    addSubscribe(){
        this.firstName.subscribe(this.search.bind(this));
        this.lastName.subscribe(this.search.bind(this));
        this.birthday.subscribe(this.search.bind(this));
        this.index.subscribe(this.search.bind(this));
    }

    search(){
        var mapping = {
            create: function(options) {
                return new Student(options.data);
            }
        };
        var students = JSON.parse($.ajax({
            url: "http://localhost:8080/myapp/students?" + this.getSearchParametersString(),
            method: "GET",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            },
        }).responseText);
        ko.mapping.fromJS(students,mapping,this.studentsObservableArray);
        console.log("index="+ko.toJS(this.index)+"&firstName="+ko.toJS(this.firstName)+"&lastName="+ko.toJS(this.lastName)+"&birthday="+ko.toJS(this.birthday))
        
    }

    getSearchParametersString(){
        return "index="+ko.toJS(this.index)+"&firstName="+ko.toJS(this.firstName)+"&lastName="+ko.toJS(this.lastName)+"&birthday="+ko.toJS(this.birthday)
    }
}
class CourseSearch extends Course{
    constructor(observableArray ,data){
        super(data);
        this.coursesObservableArray = observableArray;
    }

    addSubscribe(){
        this.name.subscribe(this.search.bind(this));
        this.lecturer.subscribe(this.search.bind(this));
        this.id.subscribe(this.search.bind(this));
    }

    search(){
        var mapping = {
            create: function(options) {
                return new Course(options.data);
            }
        };
        var courses = JSON.parse($.ajax({
            url: "http://localhost:8080/myapp/courses?" + this.getSearchParametersString(),
            method: "GET",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            },
        }).responseText);
        console.log(courses);
        ko.mapping.fromJS(courses,mapping,this.coursesObservableArray);
    }

    getSearchParametersString(){
        return "id="+ko.toJS(this.id)+"&name="+ko.toJS(this.name)+"&lecturer="+ko.toJS(this.lecturer);
    }
}
class GradeSearch extends Grade{
    constructor(observableArray ,data,student){
        super(data,student);
        this.gradesObservableArray = observableArray;
    }

    addSubscribe(){
        this.id.subscribe(this.search.bind(this));
        this.value.subscribe(this.search.bind(this));
        this.course.subscribe(this.search.bind(this));
        this.date.subscribe(this.search.bind(this));
    }

    search(){
        var mapping = {
            create: function(options) {
                return new Grade(options.data,this.student);
            }
        };
        var grades = JSON.parse($.ajax({
            url: "http://localhost:8080/myapp/students/"+this.student+"/grades/?" + this.getSearchParametersString(),
            method: "GET",
            async: false,
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json"
            },
        }).responseText);
        console.log("value="+ko.toJS(this.value)+"&date="+ko.toJS(this.date));
        ko.mapping.fromJS(grades,mapping,this.gradesObservableArray);
    }

    getSearchParametersString(){
        return "value="+ko.toJS(this.value)+"&date="+ko.toJS(this.date);
    }
}
var viewModel = function(){
    var self = this;

    this.students = ko.observableArray([]);
    this.studentToAdd = new Student();
    this.studentSearch = new StudentSearch(this.students);
    this.courses = ko.observableArray([]);
    this.courseToAdd = new Course();
    this.courseSearch = new CourseSearch(this.courses);
    this.grades = ko.observableArray([]);
    this.gradeToAdd = new Grade();
    this.gradeSearch = new GradeSearch(this.grades);
    this.currentStudent = new Student({firstname: "", lastname: "", birthday: ""},false);

    this.getStudents = function() {
        var mapping = {
            create: function(options) {
                return new Student(options.data);
            }
        };
        var student = JSON.parse($.ajax({
            url: url + 'students',
            async: false,
            headers: {
                Accept: "application/json",
            }
        }).responseText);
        ko.mapping.fromJS(student,mapping,self.students);
    }
    this.getStudents();
    this.addStudent = function(){
        self.studentToAdd.POST();
        self.getStudents();
        ko.mapping.fromJS(new Student,{},self.studentToAdd);
    }
    this.deleteStudent = function(student){
        student.DELETE();
        self.getStudents();
    }

    this.getCourses = function() {
        var mapping = {
            create: function(options) {
                return new Course(options.data);
            }
        };
        var courses = JSON.parse($.ajax({
            url: url + 'courses',
            async: false,
            headers: {
                Accept: "application/json",
            }
        }).responseText);
        ko.mapping.fromJS(courses,mapping,self.courses);
    }
    this.getCourses();
    this.addCourse= function(){
        self.courseToAdd.POST();
        self.getCourses();
        ko.mapping.fromJS(new Course,{},self.courseToAdd);
    }
    this.deleteCourse = function(course){
        course.DELETE();
        self.getCourses();
    }
    this.getGradesForStudent = function(index){
        var mapping = {
            create: function(options) {
                return new Grade(options.data, index);
            }
        };
        var grades = JSON.parse($.ajax({
            url: url + 'students/'+ index +'/grades',
            async: false,
            headers: {
                Accept: "application/json"
            }
        }).responseText);
        ko.mapping.fromJS(grades,mapping,self.grades);
        self.gradeToAdd.student = index;
    }
    this.addGrade = function(){
        self.gradeToAdd.POST();
        self.getGradesForStudent(self.gradeToAdd.student);
        ko.mapping.fromJS(new Grade,{},self.gradeToAdd);
    }
    this.deleteGrade = function(grade){
        grade.DELETE();
        self.getGradesForStudent(self.gradeToAdd.student);
    }
    self.onClickGrades = function(data) {
        var dataJS = ko.toJS(data);
        ko.mapping.fromJS(dataJS,{},self.currentStudent);
        self.gradeSearch.student = dataJS.index;
        if(typeof dataJS.index !== 'undefined'){
            self.getGradesForStudent(dataJS.index);
        }
        window.location = "#grades";
    }
}
var  newViewModel = new viewModel();

ko.applyBindings(newViewModel);