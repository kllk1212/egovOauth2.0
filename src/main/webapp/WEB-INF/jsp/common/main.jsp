<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/member/style.css" rel="stylesheet"
	type="text/css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.19.0/axios.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>
</head>
<body id="body">
	<br />
	<h1 align="center">Main page</h1>
	<table>
		<tr>
			<td><button onclick="window.location='/board/list'">게시판</button></td>
		</tr>
		<tr>
			<td><button onclick="window.location='/common/test'">테스트로그인</button></td>
		</tr>
		<tr>
			<td><button onclick="window.location='/common/signup'">회원가입</button></td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<button id="logoutBtn">로그아웃</button>
			</td>
		</tr>
	</table>
	<br />
	<br />
	<br />
	<div align="center">
		<img src="/resources/member/imgs/beach.jpg" width="500" />
	</div>
	<br />
	<br />
	<script>
$(document).ready(function(){
	$("#body").click(function(){
		var aaToken = $.cookie("aaToken");
		var rrToken = $.cookie("rrToken");
		var userNum = $.cookie("userNum");
		
		var tokens = {
				rrToken: rrToken,
				aaToken: aaToken
			
		};
		
		console.log("쿠키에 저장된 aa토큰 : "+aaToken);
		console.log("쿠키에 저장된 rr토큰 : "+rrToken);
		console.log("쿠키에 저장된 userNum : "+ userNum);
		if(aaToken ===undefined&&rrToken !=undefined){
			console.log("a토큰 만료");
			axios.post('http://localhost:8080/api/aTokenCreate',tokens)
				  .then(function (data) {
					console.log(data);
					console.log("현재 알토큰 : "+tokens.rrToken);
					console.log("현재 userNum : "+ userNum);
					var aatokenDate = new Date();
					aatokenDate.setTime(aatokenDate.getTime() + 1*10*1000); // 중앙값 60으로바꾸면 1분 // 현재 10초
					$.cookie('aaToken',data.data.aaToken,{ expires: aatokenDate,path: 'http://localhost:8080/'});
				  })
				  .catch(function (error) {
				    console.log(error);
				    console.log("현재 알토큰 : "+tokens.rrToken);
				  });  			
		} // if
	});// body
	$("#logoutBtn").on("click",function(){
		var aaToken = $.cookie("aaToken");
		var rrToken = $.cookie("rrToken");
		var userNum = $.cookie("userNum");
		var tokens = {
				rrToken: rrToken,
				aaToken: aaToken,
				userNum: userNum
		};		
		axios.post('http://localhost:8080/api/logout',tokens)
		  .then(function (data) {
			console.log(data);
			console.log("현재 에이토큰 : "+tokens.aaToken);
			console.log("현재 알토큰 : "+tokens.rrToken);
			console.log("현재 유저번호 : "+tokens.userNum);
			$.removeCookie('aaToken',{ path: 'http://localhost:8080/'});
			$.removeCookie('rrToken',{ path: 'http://localhost:8080/'});
			$.removeCookie('userNum',{ path: 'http://localhost:8080/'});
			window.location="/common/main";
		  
		  })
		  .catch(function (error) {
		    console.log(error);
		    console.log("현재 알토큰 : "+tokens.rrToken);
			console.log("현재 유저번호 : "+tokens.userNum);
		  });
	}); // $("#btn").on("click",function(){
}); //$(document).ready(function(){

</script>
</body>
</html>