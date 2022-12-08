<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="/resources/member/style.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<br />
	<h1 align="center"> 회원가입 </h1>
	<form action="/api/signup" method="post">
		<input type="hidden" name="sns" value="site">
		<table>
			<tr>
				<td>아이디 *</td>
				<td><input type="text" name="userId" id="userId" /></td>
			</tr>
			<tr>
				<td>아이디 사용가능 여부</td>
				<td><input type="text" id="checkResult" disabled /></td>
			</tr>
			<tr>
				<td>닉네임 *</td>
				<td><input type="text" name="nickName" /></td>
			</tr>
			<tr>
				<td>비밀번호 *</td>
				<td><input type="password" name="userPw" /></td>
			</tr>
			<tr>
				<td>비밀번호 확인 *</td>
				<td><input type="password" name="pwch" /></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>성별 </td>
				<td>
					남 <input type="radio" name="gender" value="male" checked />
					여 <input type="radio" name="gender" value="female" />
				</td>
			</tr>
			<tr>
				<td>email</td>
				<td>
					<input type="text" name="email" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="회원 가입" />
					<input type="reset" value="재작성" />
					<input type="button" value="취소" onclick="window.location='/common/main'" />
				</td>
			</tr>
		</table>
	</form>
	<div align="center"><input type="button" value="네이버로 회원가입" onclick="window.location='https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=xGYVtGy2jTxj9ocjlhyj&state=state&redirect_uri=http://localhost:8080/login/oauth2/code/naver'" /></div>
	<div align="center"><input type="button" value="카카오로 회원가입" onclick="window.location='https://kauth.kakao.com/oauth/authorize?client_id=c8b8e5a6dfb657aa30f9fbb8b1b6d5fd&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code'" /></div>
	
	<script>
	/*
	$(document).ready(function(){
		$("#id").change(function(){  // id 입력란에 값을 입력했을때, 
			// id 입력란에 사용자가 입력한 값이 필요 
			let idVal = $("#id").val();
			//console.log(idVal); 출력해서 확인하기!! 
			
			// 꺼낸 입력값을 서버에 보내서 DB에 동일한 id가 있는지 체크
			$.ajax({
				type: "post", 
				url: "/member/idAvail",
				data: {id : idVal}, 
				success: function(result){
					console.log("success"); 
					console.log(result);
					// 결과를 아이디사용가능여부 input 태그의 value값으로 띄워 주기
					$("#checkResult").val(result);
				}, 
				error: function(e){ 
					console.log(e); 
				}
			}); 
		}); 
	});*/
	</script>
</body>
</html>