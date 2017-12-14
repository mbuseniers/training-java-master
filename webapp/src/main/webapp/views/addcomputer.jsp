<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
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
					<h1>
						<spring:message code="label.titreAddComputer" />
					</h1>
					<h2>
						<c:out value="${messageAjout}" />
					</h2>
					<form:form action="addcomputer" method="POST"
						modelAttribute="computerDTO">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="label.computerName" /></label>
								<form:input class="form-control" path="name"
									placeholder="Computer name" />
								<form:errors class="errorForm" path="name" />
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="label.dateIntroduced" /></label>
								<form:input class="form-control" path="dateIntroduced"
									placeholder="yyyy-MM-dd" />
								<form:errors class="errorForm" path="dateIntroduced" />
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="label.dateDiscontinued" /></label>
								<form:input class="form-control" path="dateDiscontinued"
									placeholder="yyyy-MM-dd" />
								<form:errors class="errorForm" path="dateDiscontinued" />

							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="label.company" /></label>
								<spring:message code="label.selectCompany" var="selectCompanyText"/>
								<form:select path="companyId" class="form-control">
									<form:option value="0" label="${selectCompanyText}" />" />
									<form:options items="${mapCompanies}" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>