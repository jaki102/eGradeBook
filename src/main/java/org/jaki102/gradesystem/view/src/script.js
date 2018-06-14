"use strict";
$(document).ready(function () {
var students = ko.observableArray();
var courses = ko.observableArray();
var grades = ko.observableArray();
var $tableStudent = $('#studentTable');
var $tableCourse = $('#coursesTable');
var $index = $('#index');
var $firstName = $('#firstName');
var $lastName = $('#lastName');
var $birthday = $('#birthday');
var studentTemplate = "" +
'<tr>'+
'<td><input type="number" name="index" value="{{index}}" min="0" readonly></td>'+
'<td><input type="text" name="firstName" value="{{firstName}}"></td>'+
'<td><input type="text" name="lastName" value="{{lastName}}"></td>'+
'<td><input type="text" name="birthday" onfocus="(this.type=\'date\')" onblur="(this.type=\'text\')" value="{{birthday}}"></td>'+
'<td>'+
    '<button onclick="window.location.href=\'#grades\'" data-id = "{{index}} id ="seegradetable">See grades</button>'+
    '<button type="button">Modify</button>'+
    '<button type="button">Delete</button>'+
'</td>'+
'</tr>';

var courseTemplate = "" +
'<tr>' +
'<td><input type="text" name="name" value="{{name}}"></td>' +
'<td><input type="text" name="lecturer" value="{{lecturer}}"></td>' +
'<td>' +
    '<button type="button">Modify</button>' +
    '<button type="button">Delete</button>' +
'</td>' +
'</tr>'
function addStudentRow(student) {
    $tableStudent.append(Mustache.render(studentTemplate, student));
}
function addCourseRow(course) {
    $tableCourse.append(Mustache.render(courseTemplate, course));
}
    $.ajax({
        type: 'GET',
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        url: 'http://localhost:8080/myapp/students',
        success: function (data) {
            ko.mapping.fromJS(data, students);
            $.each(data, function(i, student){
                addStudentRow(student);
            });
            console.log(data)
        },
        error: function(){
            alert('error loading students');
        }
    });

    $.ajax({
        type: 'GET',
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        url: 'http://localhost:8080/myapp/courses',
        success: function (dataCourse) {
            ko.mapping.fromJS(dataCourse, courses);
            $.each(dataCourse, function(i, course){
                addCourseRow(course);
            });
            console.log(dataCourse)
        },
        error: function(){
            alert('error loading courses');
        }
    });

     $("#seegradetable").on("click", function(){
        $.ajax({
            type: 'GET',
            headers: {
                Accept: "application/json; charset=utf-8"
            },
            url: 'http://localhost:8080/myapp/students/1/grades',
            success: function (dataGrade) {
                ko.mapping.fromJS(dataGrade, grades);
                 $.each(dataGrade, function(i, grade){
                    
                });
                console.log(dataGrade);
            },
            error: function(){
                alert('error loading grades');
            }
        })
})
});

    // $('#addStudent').on('click', function(){

    //     var newStudent = {
    //         index: $index.val(),
    //         firstName: $firstName.val(),
    //         lastName: $lastName.val(),
    //         birthday: $birthday.val(),
    //     }

    //     $.ajax({
    //         type: 'POST',
    //         headers: {
    //             Accept: "application/json; charset=utf-8"
    //         },
    //         url: 'http://localhost:8080/myapp/students',
    //         data: ko.mapping.toJS(newStudent),
    //         success: function (data) {
                
    //             students.push(data);
    //             addStudent(newStudent);
    //         },
    //         error: function(){
    //             alert('error creating students');
    //         }
    //     });

    // });
