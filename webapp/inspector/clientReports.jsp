<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
   <body>
   <script>
      $(function() {
        $(".btn").click(
          function() {
            var reportId = $(this).attr('data-reportId');

            $("#hide1").attr('value', reportId);
          })
      });
   </script>
	<style>
	.logout {
      margin-right:10px;
    }
    .reports {
    margin-left:50px;
    }
    .filter{
        margin-right:100px;
    }
    .clients {
     margin-right:10px;
      margin-left:10px;
    }
    table {
       counter-reset: tableCount;
       table-layout: fixed;
       margin: auto;
    }
    td {
       word-wrap:break-word;
       text-align: center;
    }
    tr {
       text-align: center;
    }
      .counterCell:before {
         content: counter(tableCount);
         counter-increment: tableCount;
    }
    </style>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
				<form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout" class = "logout">
                   <button type="submit" class="btn btn-primary"><lan:print message="logout"/></button>
                </form>
                <form action="/tax-office/service/home" method="GET">
                   <button type="submit" class="btn btn-primary"><lan:print message="home"/></button>
                </form>
                <form action = "/tax-office/service/client"  method="GET"  class = "clients">
                   <input type="hidden" name="page" value="1">
                   <button type="submit" class="btn btn-primary"><lan:print message="all_clients"/></button>
                </form>
                <form action = "/tax-office/service/report"  method="GET" >
                <input type="hidden" name="page" value="1"/>
                   <button type="submit" class="btn btn-primary"><lan:print message="all_reports"/></button>
                </form>
		</nav>
	</header>
 <br>
    <h3 class="text-center"><lan:print message="reports_of"/>${clientLogin}</h3>
 <hr>
      <form action = "/tax-office/service/report/client/filter"  method="GET" class = "filter" align = "right">
            <div class="form-group">
               <label for="name"><lan:print message="choose_date"/>:</label>
                 <input type="date" name="date"
                  min="01-01-2010" max="12-31-2100"/>
               <label for="name"><lan:print message="choose_status"/>:</label>
                  <select name="status">
                    <option value=""><lan:print message="status"/></option>
                    <option value="SUBMITTED"><lan:print message="submitted"/></option>
                    <option value="ACCEPTED"><lan:print message="accepted"/></option>
                    <option value="UNACCEPTED"><lan:print message="unaccepted"/></option>
                    <option value="EDITED"><lan:print message="edited"/></option>
                  </select>
               <label for="name"><lan:print message="choose_type"/>:</label>
                  <select name="type">
                    <option value=""><lan:print message="type"/></option>
                    <option value="income statement"><lan:print message="income_statement"/></option>
                    <option value="income tax"><lan:print message="income_tax"/></option>
                    <option value="single tax"><lan:print message="single_tax"/></option>
                  </select>
               <input type="hidden" name="clientId" value="${clientId}"/>
               <input type="hidden" name="page" value="1"/>
               <button type="submit" class="btn btn-outline-dark"><lan:print message="filter"/></button>
            </div>
     </form>
     <form action = "/tax-office/service/report/client"  method="GET" class = "reports">
        <input type="hidden" name="clientId" value="${clientId}"/>
        <input type="hidden" name="page" value="1"/>
         <input type="hidden" name="clientLogin" value="${clientLogin}"/>
         <button type="submit" class="btn btn-primary"><lan:print message="all_reports_client"/></button>
     </form>
<br>
			<table class=" table-bordered " width="1400">
			<col style="width:4%">
				<thead>
					<tr>
					    <th>№</th>
						<th><lan:print message="title"/></th>
						<th><lan:print message="date"/></th>
						<th><lan:print message="type_table"/></th>
						<th><lan:print message="status_table"/></th>
						<th><lan:print message="info"/></th>
						<th><lan:print message="actions"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reports}" var="report">
						<tr>
						    <td class="counterCell"></td>
							<td><a href="/tax-office/service/report/data?id=${report.id}" target="_blank">${report.title}</a></td>
							<td><c:out value="${report.date}" /></td>
							<td><c:out value="${report.type}" /></td>
							<td><c:out value="${report.status}" /></td>
							<td><c:out value="${report.info}" /></td>
							<td>
							<button type="button" class="btn btn-outline-dark" data-reportId="${report.id}"
                                data-toggle="modal" data-target="#exampleModal"><lan:print message="not_to_accept"/>
                            </button>
                                  <form action="/tax-office/service/report/update/client" method="POST">
                                      <input type="hidden" name="updatedStatus" value="ACCEPTED"/>
                                      <input type="hidden" name="info" value="Report accepted"/>
                                      <input type="hidden" name="reportId" value="${report.id}"/>
                                      <input type="hidden" name="clientId" value="${clientId}"/>
                                      <input type="hidden" name="date" value="${dto.date}"/>
                                      <input type="hidden" name="status" value="${dto.status}"/>
                                      <input type="hidden" name="type" value="${dto.type}"/>
                                      <input type="hidden" name="page" value="${page}"/>
                                      <button type="submit" class="btn btn-outline-dark"><lan:print message="accept"/></button>
                                  </form>
                                  <a href="upload/id${clientId}/${report.title}" download >
                                      <button  class="btn btn-outline-primary"><lan:print message="download"/></button>
                                  </a>
                                  <form action="/tax-office/service/deleteReportById" method="POST" onSubmit='return confirm("<lan:print message="are_you_sure"/>");'>
                                      <input type="hidden" name="id" value="${report.id}"/>
                                      <input type="hidden" name="clientId" value="${clientId}"/>
                                      <input type="hidden" name="page" value="${page}"/>
                                      <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                                      <input type="hidden" name="title" value="${report.title}"/>
                                      <input type="hidden" name="date" value="${date}"/>
                                      <input type="hidden" name="statusFilter" value="${status}"/>
                                      <input type="hidden" name="type" value="${type}"/>
                                      <button type="submit" class="btn btn-outline-danger"><lan:print message="delete"/></button>
                                  </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test = "${countOfPages != 0}">
               <c:forEach var = "i" begin = "1" end = "${countOfPages}">
                 <form action = "/tax-office/service/report/client/filter"  method="GET" class = page style="float:left">
                    <input type="hidden" name="clientId" value="${clientId}"/><br><br>
                    <input type="hidden" name="page" value="${i}"/>
                    <input type="hidden" name="date" value="${dto.date}"/>
                    <input type="hidden" name="status" value="${dto.status}"/>
                    <input type="hidden" name="type" value="${dto.type}"/>
                    <button type="submit" class="btn btn-link" >${i}</button>
                 </form>
               </c:forEach>
            </c:if>
			<br>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
       <div class="modal-dialog" role="document">
           <div class="modal-content">
               <div class="modal-header">
                   <h5 class="modal-title" id="exampleModalLabel"><lan:print message="reason_for_unaccepted"/></h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                         <span aria-hidden="true">&times;</span>
                      </button>
               </div>
               <div class="modal-body">
                   <form action="/tax-office/service/report/update/client" method="POST">
                      <textarea rows="10" cols="45" name="info" maxlength="100" required placeholder="<lan:print message="enter_reason"/>"></textarea>
                      <input type="hidden" name="status" value="UNACCEPTED"/>
                      <input id="hide1" type="hidden" name="reportId" value=""/>
                      <input type="hidden" name="clientId" value="${clientId}"/>
                      <input type="hidden" name="date" value="${dto.date}"/>
                      <input type="hidden" name="status" value="${dto.status}"/>
                      <input type="hidden" name="type" value="${dto.type}"/>
                      <input type="hidden" name="page" value="${page}"/>
               </div>
                   <div class="modal-footer">
                       <button type="button" class="btn btn-secondary" data-dismiss="modal"><lan:print message="close"/></button>
                       <button type="submit" class="btn btn-outline-primary"><lan:print message="submit"/></button>
                   </form>
           </div>
       </div>
    </div>
  </body>
</html>