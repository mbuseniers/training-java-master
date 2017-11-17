<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    	<h2><c:out value="${messageAjout}" /></h2>
                    <form:form action="addcomputer" method="POST" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <!--<input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">-->
                                <form:input path="name" placeholder="Computer name"/>
                                <form:errors path="name" />
                                <c:out value="${messageErrorName}" />
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <!-- <input type="date" class="form-control" id="introduced" name="introduced" placeholder="yyyy-MM-dd"> -->
                                <form:input path="dateIntroduced" placeholder="yyyy-MM-dd"/>
                                <form:errors path="dateIntroduced" />
                                <c:out value="${messageErrorIntroduced}" />
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <!--  <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="yyyy-MM-dd">-->
                                <form:input path="dateDiscontinued" placeholder="yyyy-MM-dd"/>
                                <form:errors path="dateDiscontinued" />
                                <c:out value="${messageErrorDiscontinued}" />
                                
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach items="${listeCompanies}" var="company">
										 <option value=${company['id']}>${company['name']}</option>
									</c:forEach>
                                </select>
                                <c:out value="${messageErrorCompany}" />
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>