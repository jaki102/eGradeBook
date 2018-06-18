"use strict"

var url = 'http://localhost:8080/myapp/';

class Student {
    constructor(data = {firstName:"", lastName:"", birthday:""}){
        this.firstName = new ko.observable(data.firstName);
        this.lastName = new ko.observable(data.lastName);
        this.birthday = new ko.observable(data.birthday);
        this.index = new ko.observable(data.index);
        this.addSubscribe();
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
    constructor(data = {id: "", value: "", course: "", date: ""},student = 0){
        this.student = student;
        this.id = new ko.observable(data.id);
        this.value = new ko.observable(data.value);
        this.course = new ko.observable(data.course.uid);
        this.date = new ko.observable(data.date);
        this.addSubscribe();
    }

    addSubscribe(){
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

var viewModel = function(){
    var self = this;

    this.students = ko.observableArray([]);
    this.studentToAdd = new Student();
    this.courses = ko.observableArray([]);
    this.courseToAdd = new Course();
    this.grades = ko.observableArray([]);
    this.gradeToAdd = new Grade();
    this.currentStudent = new Student();

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
            url: url + 'students/'+index+'/grades',
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
        //ko.mapping.fromJS(new Grade,{},self.gradeToAdd);
    }
    this.deleteGrade = function(grade){
        grade.DELETE();
        self.getGradesForStudent(self.gradeToAdd.student);
    }
    self.onClickGrades = function(data) {
        var dataJS = ko.toJS(data);
        ko.mapping.fromJS(dataJS,{},self.currentStudent);

        if(typeof dataJS.index !== 'undefined'){
            self.getGradesForStudent(dataJS.index);
        }
        window.location = "#grades";
    }
}
var  newViewModel = new viewModel();

ko.applyBindings(newViewModel);