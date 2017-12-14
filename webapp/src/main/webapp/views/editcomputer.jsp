<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${computerDTO.id}" />

					</div>
					<h1>Edit Computer</h1>
					<h2>
						<c:out value="${messageEdit}" />
					</h2>

					<form:form action="editcomputer" method="POST"
						modelAttribute="computerDTO">
						<input type="hidden" value="0" id="id" />
						<fieldset>

							<form:input type="hidden" class="form-control" path="id"
								value="${id}" />
							<div class="form-group">
								<label for="computerName">Computer name</label>
								<form:input class="form-control" path="name" 
									placeholder="Computer name" />
								<form:errors class="errorForm" path="name" />
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label>
								<form:input class="form-control" path="dateIntroduced"
									placeholder="yyyy-MM-dd" />
								<form:errors class="errorForm" path="dateIntroduced" />
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label>
								<form:input class="form-control" path="dateDiscontinued"
									placeholder="yyyy-MM-dd"  />
								<form:errors class="errorForm" path="dateDiscontinued" />
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<spring:message code="label.selectCompany" var="selectCompanyText"/>
								<form:select path="companyId" class="form-control">
									<form:option value="0" label="${selectCompanyText}" />
									<form:options items="${mapCompanies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>