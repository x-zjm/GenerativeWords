<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   	
   	<div id="twoterm" class="term-content">
	   	<span class="termlab">二字词语：</span>
	   	<div class="termcon">
		   	<c:forEach items="${list }" var="item" begin="0" end="${listtwosize-1 == -1 ? 0 : listtwosize-1}" step="1" >
				<span class="termitem">${item == '0' ? '' : item}</span>
			</c:forEach>
		</div>
		<span class="termsum">共计：${list[listtwosize+listthreesize+listfoursize] }</span>
	</div>
	<div id="threeterm" class="term-content">
		<span class="termlab">三字词语：</span>
		<div class="termcon">
			<c:forEach items="${list }" var="item" begin="${listtwosize }" end="${listtwosize+listthreesize-1 == -1 ? 0 : listtwosize+listthreesize-1}" step="1" >
				<span class="termitem">${item == '0' ? '' : item}</span>
			</c:forEach>
		</div>
		<span class="termsum">共计：${list[listtwosize+listthreesize+listfoursize+1] }</span>
	</div>
	<div id="fourterm" class="term-content">
		<span class="termlab">四字词语：</span>
		<div class="termcon">
			<c:forEach items="${list }" var="item" begin="${listtwosize+listthreesize }" end="${listtwosize+listthreesize+listfoursize-1 == -1 ? 0 :listtwosize+listthreesize+listfoursize-1}" step="1" >
				<span class="termitem">${item == '0' ? '' : item}</span>
			</c:forEach>
		</div>
		<span class="termsum">共计：${list[listtwosize+listthreesize+listfoursize+2] }</span>
	</div>