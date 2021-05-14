<%@page import="model.InnovativeProject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Innovative Projects</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/InnovativeProjects.js"></script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Innovative Projects Management</h1>
				<div class="col-md-6">
					<div class="form-group">

						<form id="formItem" name="formItem" method="post"
							action="InnovativeProjects.jsp">

							Project Name : <input id="projectName" name="projectName"
								type="text" class="form-control""><br> Project
							Price: <input id="projectPrice" name="projectPrice" type="text"
								class="form-control"><br>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="lblName">Project
										Type: </span>
								</div>
								<select id="projectType" name="projectType">
									<option value="0">--Select Type--</option>
									<option value="1">Type One</option>
									<option value="2">Type two</option>
								</select>
							</div>

							Project Description: <input id="projectDescription"
								name="projectDescription" type="text" class="form-control"><br>

							Quantity: <input id="quantity" name="quantity" type="text"
								class="form-control"><br> 
								
								<input id="hidItemIDSave"
								type='hidden' name="hidItemIDSave" >

							<div id="alertSuccess" class="alert alert-success"></div>
							<div id="alertError" class="alert alert-danger"></div>

							<input id="btnSave" name="btnSave" type="button" value="Save"
								" class="btn btn-primary">
						</form>

					</div>
				</div>

				<div class="alert alert-success">
					<%
						//out.print(session.getAttribute("statusMsg"));
					%>
				</div>


				<br>
<div id="divItemsGrid">
				<%
					// call the readItems method

					InnovativeProject iproject = new InnovativeProject();
					out.print(iproject.readInnovativeProjects());
				%>
</div>

				<br>

			</div>
		</div>
	</div>

</body>
</html>