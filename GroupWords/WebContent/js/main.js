/**
 * 主要的js代码
 */
function inputchar() {

	var effectchar = document.getElementById("effectChar").value;

	$.post("${pageContext.request.contextPath }/term",
			"method=inputchar&effectchar=" + effectchar, function(data) {
				$(".content").html(data).show();
			});
}

function randchar() {

	var sizeselect = document.getElementById("sizeselect").value;

	$.post("${pageContext.request.contextPath }/term",
			"method=randchar&sizeselect=" + sizeselect, function(data) {
				$("#ter").html(data);
			});
	$("#top-left-term").show();
}

function cha() {

	$.post("${pageContext.request.contextPath }/term", "method=sortchar",
			function(data) {
				$(".content").html(data).show();
			});
}

$(function() {

	var select = document.getElementById("sizeselect");

	for (var i = 2; i <= 10; i++) {
		var opt = document.createElement("option");
		opt.value = i;
		opt.innerHTML = i;
		select.appendChild(opt);
	}

	$("#top-left-utils").hide();
	$("#top-right-utils").hide();

	$("#randinput").click(function() {
		if (!$("#top-right-utils").is(":hidden")) {
			$("#top-right-utils").hide();
		}
		if (!$(".content").is(":hidden")) {
			$(".content").hide();
		}
		if ($("#top-left-utils").is(":hidden")) {
			$("#top-left-utils").show();
			$("#top-left-term").hide();
		}
		$("#rand").css({
			"background-color" : "#FFFFFF",
			"color" : "#0057FE"
		});
		$("#manual").css({
			"background-color" : "#0057FE",
			"color" : "#FFFFFF"
		});

		$("#sizeselect").focus();
	});

	$("#manualinput").click(function() {
		if (!$("#top-left-utils").is(":hidden")) {
			$("#top-left-utils").hide();
		}
		if (!$(".content").is(":hidden")) {
			$(".content").hide();
		}
		if ($("#top-right-utils").is(":hidden")) {
			$("#top-right-utils").show();
		}
		$("#manual").css({
			"background-color" : "#FFFFFF",
			"color" : "#0057FE"
		});
		$("#rand").css({
			"background-color" : "#0057FE",
			"color" : "#FFFFFF"
		});
		$("#effectChar").focus();
	});

	$('.btn').mouseover(function() {
		$(".btn").css({

		});
	});
	$('.btn').mouseout(function() {
		$(".btn").css({
			"background-color" : "#0057FE",
		});
	});
});