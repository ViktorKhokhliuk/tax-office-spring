<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of Clients</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
   <body>
	<style>
	.logout {
      margin-right:10px;
    }
    .search{
        margin-right:100px;
    }
    .clients {
        margin-left:150px;
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
		</nav>
	</header>
 <br>
 	<h3 class="text-center"><lan:print message="list_clients"/></h3>
 	<hr>
 <form action = "/tax-office/service/client/search" method="GET" class = "search" align="right">
       <div class="form-group">
          <label for="name"><lan:print message="name"/>:</label>
          <input type="text" name="name" placeholder="<lan:print message="enter_name"/>"/>
          <label for="surname"><lan:print message="surname"/>:</label>
          <input type="text" name="surname" placeholder="<lan:print message="enter_surname"/>"/>
          <label for="tin"><lan:print message="tin"/>:</label>
          <input type="text" name="tin"= placeholder="<lan:print message="enter_tin"/>"/>
          <input type="hidden" name="page" value="1">
          <button type="submit" class="btn btn-outline-dark"><lan:print message="search"/></button>
       </div>
 </form>
       <form action = "/tax-office/service/client"  method="GET" class = "clients">
           <input type="hidden" name="page" value="1">
           <button type="submit" class="btn btn-primary"><lan:print message="all_clients"/></button>
       </form>
<br>
			<table class=" table-bordered " width="1200">
			<col style="width:4%">
    				<thead>
					<tr>
					    <th>???</th>
						<th><lan:print message="login"/></th>
						<th><lan:print message="name"/></th>
						<th><lan:print message="surname"/></th>
						<th><lan:print message="tin"/></th>
						<th><lan:print message="actions"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clients}" var="client">
						<tr>
						    <td class="counterCell"></td>
							<td><c:out value="${client.login}" /></td>
							<td><c:out value="${client.name}" /></td>
							<td><c:out value="${client.surname}" /></td>
							<td><c:out value="${client.tin}" /></td>
							<td>
                               <form action = "/tax-office/service/report/client" method = "GET">
							     <input type="hidden" name="clientId" value="${client.id}"/>
							     <input type="hidden" name="page" value="1">
							     <input type="hidden" name="clientFullName" value="${client.name} ${client.surname}"/>
							     <button type="submit" class="btn btn-outline-info"><lan:print message="reports"/></button>
							   </form>
							   <form action="/tax-office/service/client/delete" method="POST" onSubmit='return confirm("<lan:print message="are_you_sure"/>");'>
                                 <input type="hidden" name="id" value="${client.id}"/>
                                 <input type="hidden" name="page" value="${page}">
                                 <input type="hidden" name="name" value="${name}"/>
                                 <input type="hidden" name="surname" value="${surname}"/>
                                 <input type="hidden" name="tin" value="${tin}"/>
                                 <button type="submit" class="btn btn-outline-danger"><lan:print message="delete"/></button>
                               </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
	</div>
	<c:if test = "${countOfPages != 0}">
         <c:forEach var = "i" begin = "1" end = "${countOfPages}">
             <form action = "/tax-office/service/client/search"  method="GET" class = page style="float:left">
                     <input type="hidden" name="page" value="${i}"/>
                     <input type="hidden" name="name" value="${name}"/>
                     <input type="hidden" name="surname" value="${surname}"/>
                     <input type="hidden" name="tin" value="${tin}"/>
                     <button type="submit" class="btn btn-link" >${i}</button>
             </form>
         </c:forEach>
    </c:if>
</body>
</html>