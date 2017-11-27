<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<h1 id="homeTitle">

				<c:out value="${nombreComputers}" />
				Computers found
			</h1>
			<h2>
				<c:out value="${messageDelete}" />
			</h2>
			<h2>
				<c:out value="${messageErreurSearch}" />
			</h2>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="POST" class="form-inline">

						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" /> 
						<input type="hidden" name="actionType" value="SEARCH">
						<input type="submit" name="searchBy" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
						<input type="submit" name="searchBy" id="searchsubmit" style = "margin-left : 5px;" value="Filter by company" class="btn btn-primary" /> 
						<input type="hidden" name="actionType" value="SEARCH">
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addcomputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deletecomputers" method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="actionType" value="DELETE">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<th>Discontinued date</th>
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">



					<c:forEach items="${liste}" var="computer">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer['id']}"></td>
							<td><a
								href="editcomputer?id=${computer['id']}&name=${computer['name']}&introduced=${computer['dateIntroduced']}&discontinued=${computer['dateDiscontinued']}&company=${computer.companyId}"
								onclick=""><c:out value="${computer.name}" /></a></td>
							<td><c:out value="${computer['dateIntroduced']}" /></td>
							<td><c:out value="${computer['dateDiscontinued']}" /></td>
							<td><c:out value="${computer.companyName}" /></td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="?changePage=1&page=<c:out value="${page-1}"/>&size=<c:out value="${size}"/> "
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>

				<c:forEach begin="${intervalMin}" end="${intervalMax}" var="i">
					<li><a ${i == page ? 'style="font-weight: bold;"' : ''}
						href="?changePage=1&page=<c:out value="${i}"/>&size=<c:out value="${size}"/> ">
							<c:out value="${i+1}" />
					</a></li>
				</c:forEach>

				<li><a
					href="?changePage=1&page=<c:out value="${page+1}"/>&size=<c:out value="${size}"/>"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">

				<c:if test="${size == 10}">
					<a href="dashboard?changeSize=1&size=10" type="button">
						<button style="color: white; background-color: #337ab7;"
							type="button" class="btn btn-default">10</button>
					</a>
				</c:if>
				<c:if test="${size != 10}">
					<a href="dashboard?changeSize=1&size=10" type="button">
						<button type="button" class="btn btn-default">10</button>
					</a>
				</c:if>

				<c:if test="${size == 50}">
					<a href="dashboard?changeSize=1&size=50" type="button">
						<button style="color: white; background-color: #337ab7;"
							type="button" class="btn btn-default">50</button>
					</a>
				</c:if>

				<c:if test="${size != 50}">
					<a href="dashboard?changeSize=1&size=50" type="button">
						<button type="button" class="btn btn-default">50</button>
					</a>
				</c:if>


				<c:if test="${size == 100}">
					<a href="dashboard?changeSize&size=100" type="button">
						<button style="color: white; background-color: #337ab7;"
							type="button" class="btn btn-default">100</button>
					</a>
				</c:if>

				<c:if test="${size != 100}">
					<a href="dashboard?changeSize&size=100" type="button">
						<button type="button" class="btn btn-default">100</button>
					</a>
				</c:if>



			</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>