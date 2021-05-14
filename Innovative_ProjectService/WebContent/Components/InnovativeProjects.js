
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//on click listner for the save button
$(document).on("click", "#btnSave", function(event) {

	// 1 - clear the alert boxes (success and error)
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();

	$("#alertError").text("");
	$("#alertError").hide();

	// then we need to call the validate method
	var status = validateItemform();

	console.log(status);

	// if not properly validated
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// if valid.....submit the form
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	//	$("#formItem").submit();
	$.ajax({
		url : "InnovativeProjectAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});

});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

//handling update button click evenr
//implementing the update button handler
$(document).on(
		"click",
		".student .btnUpdate",
		function(event) {

			console.log("Update Triggered -");

			// getting the hidden column value of which the clicked update
			// button exist
			$("#hidItemIDSave").val($(this).data("iprojectid"));
			// loading the data to the form again
			$("#projectName").val(
					$(this).closest("div").find('.card-body .pname').text());
			$("#projectPrice").val(
					$(this).closest("div").find('.card-body .pprice').text());
			$("#projectType").val(
					$(this).closest("div").find('.card-body .ptype').text());
			$("#projectDescription").val(
					$(this).closest("div").find('.card-body .pdesc').text());
			$("#quantity").val(
					$(this).closest("div").find('.card-body .pqty').text());

		});



$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "InnovativeProjectAPI",
		type : "DELETE",
		data : "itemID=" + $(this).data("iprojectid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});



function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}




//********************************************************
function validateItemform() {
	// NAME
	if ($("#projectName").val().trim() == "") {
		return "Insert project Name.";
	}
	// PRICE-------------------------------
	if ($("#projectPrice").val().trim() == "") {
		return "Insert project Price.";
	}

	// is numerical value
	var tmpPrice = $("#projectPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Project Price.";
	}
	// convert to decimal price
	$("#projectPrice").val(parseFloat(tmpPrice).toFixed(2));

	// Type------------------------
	if ($('#projectType').val() == "0") {
		return "Select Project Type";
	}

	// DESCRIPTION------------------------
	if ($("#projectDescription").val().trim() == "") {
		return "Insert Project Description.";
	}
	quantity
	//Project Quantity
	// PRICE-------------------------------
	if ($("#quantity").val().trim() == "") {
		return "Insert project Quantity.";
	}
	// is numerical value
	var tmpPrice = $("#quantity").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Project Quantity.";
	}
	return true;
}
