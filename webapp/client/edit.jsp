<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
    <body>
    <script>
        function close_window() {
          if (confirm("Close Window?")) {
            close();
          }
        }
        </script>
    <p>
    <h3 style="color:green" class="text-center">${message}</h3>
<div class="container">
  <h1><lan:print message="edit_form"/>:</h1>
  <div class="card">
   <div class="card-body">
    <form action="/tax-office/service/report/data/edit" method="POST">

     <div class="form-group row">
      <label for="name" class="col-sm-2 col-form-label"><lan:print message="name"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.name}" class="form-control" name="name"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label"><lan:print message="surname"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.surname}" class="form-control" name="surname"
        readonly disabled>
      </div>
     </div>

     <div class=" form-group row">
      <label for="tin" class="col-sm-2 col-form-label"><lan:print message="tin"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.tin}" class="form-control" name="tin"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="person" class="col-sm-2 col-form-label"><lan:print message="person"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.person}" class="form-control" name="person"
        required pattern = "(natural){1}|(legal){1}|(физическое){1}|(юридическое){1}"
        title="<lan:print message="person_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="nationality" class="col-sm-2 col-form-label"><lan:print message="nationality"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.nationality}" maxlength="20" class="form-control" name="nationality"
         required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
         title="<lan:print message="nationality_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="year" class="col-sm-2 col-form-label"><lan:print message="year"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.taxYear}" class="form-control" name="taxYear"
        required  required pattern = "^[1-9][0-9]{3}$"
        title="<lan:print message="year_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="quarter" class="col-sm-2 col-form-label"><lan:print message="quarter"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "4" value = "${reportData.quarter}" class="form-control" name="quarter"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="month" class="col-sm-2 col-form-label"><lan:print message="month"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "12" value = "${reportData.monthNumber}" class="form-control" name="monthNumber"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="group" class="col-sm-2 col-form-label"><lan:print message="group"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.taxGroup}" class="form-control" name="taxGroup"
        required pattern = "(I){1}|(II){1}|(III){1}|(IV){1}"
        title="<lan:print message="group_pattern"/>">
      </div>
     </div>

     <div class=" form-group row">
      <label for="activity" class="col-sm-2 col-form-label"><lan:print message="activity"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.activity}" maxlength="30" class="form-control" name="activity"
         required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
         title="<lan:print message="activity_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="income" class="col-sm-2 col-form-label"><lan:print message="income"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportData.income}"  maxlength="20" class="form-control" name="income"
        required pattern = "[0-9]*"
        title="<lan:print message="income_pattern"/>">
      </div>
     </div>
     <input type="hidden" name="id" value="${reportData.id}"/>
     <button type="submit" class="btn btn-primary"><lan:print message="edit"/></button>
    </form>
    <br>
    <input type="button" class="btn btn-secondary" onclick="close_window();return false;" value=<lan:print message="back"/>>
   </div>
  </div>
 </div>
</body>
</html>

