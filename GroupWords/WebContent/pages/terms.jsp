<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico" />
	<title>由字组词</title>
</head>
<body>
	<div class="main">
		<div class="top">
			<div id="randinput" class="top-left">
				<a id="rand" href="#">随机输入</a>
			</div>
			<div id="manualinput" class="top-right">
				<a id="manual" href="#">手动输入</a>
			</div>
		</div>
		<!-- top结束 -->
		<div id="top-left-utils" class="utils">
			<div id="top-left-btn">
				<select id="sizeselect"></select>
				<button class="btn" onclick="randchar()">生成汉字</button>
			</div>
			<div id="top-left-term">
				<span class="term">随机汉字：<span id="ter"></span></span>
				<div class="scbtn"><button class="btn" onclick="cha()">组词</button></div>
			</div>
		</div>

		<div id="top-right-utils" class="utils">
			<input class="input-text" id="effectChar" type="text" />
			<button class="btn" onclick="inputchar()">确定</button>
		</div>
		<!-- utils结束 -->
		<div class="content"></div>
	</div>
	<!-- main结束 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	<script type="text/javascript">
		function inputchar() {
			
			var effectchar = document.getElementById("effectChar").value;
			var len = document.getElementById("effectChar").value.length;
			
			if (len == 1 || len == 0) {
				alert("输入字数不能小于2");
			} else if(!/^[\u4e00-\u9fa5]+$/gi.test(document.getElementById("effectChar").value)){
				alert("请输入正确汉字");
				$("#effectChar").val("");
			} else {
				$.post("${pageContext.request.contextPath }/term",
						"method=inputchar&effectchar="+effectchar,
						function(data) {
							$(".content").html(data).show();
						});
			}
		}
	
		function randchar() {

			var sizeselect = document.getElementById("sizeselect").value;

			$.post("${pageContext.request.contextPath }/term",
					"method=randchar&sizeselect="+sizeselect,
					function(data) {
						$("#ter").html(data);
					});
			$("#top-left-term").show();
		}
		
		function cha() {
			
			$.post("${pageContext.request.contextPath }/term",
					"method=sortchar",
					function(data) {
						$(".content").html(data).show();
					});
		}
	
		$(function() {
			
			var select = document.getElementById("sizeselect");

			for (var i=2; i<=10; i++) {
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
	</script>
</body>
</html>