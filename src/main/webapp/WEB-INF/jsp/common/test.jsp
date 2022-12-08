<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테스트페이지</title>
</head>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.19.0/axios.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>

<body>
	<h1>테스트 로그인</h1>

	<div>
		<form>
			<table>
				<tr>
					<td>아이디 :</td>
					<td><input type="text" id="userId"></td>
				</tr>
				<tr>
					<td>비밀번호 :</td>
					<td><input type="password" id="userPw"></td>
				</tr>
				<tr>
					<td>
						<button type="button" id="btn">로그인</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script>
$(document).ready(function(){
	$("#btn").on("click",function(){
		console.log("되나?");
		var id = $("#userId").val();
		var pw = $("#userPw").val();
	    var vo = {
	            userId: id,
	            userPw: pw
	        };
		axios.post('http://localhost:8080/api/login',vo )
			  .then(function (data) {

				var aatokenDate = new Date();
				var rrtokenDate = new Date();
				aatokenDate.setTime(aatokenDate.getTime() + 1*10*1000); // 중앙값 60으로바꾸면 1분 // 현재 10초
				rrtokenDate.setTime(rrtokenDate.getTime() + 10*60*1000); // 중앙값 60으로바꾸면 1분 // 현재 10분  -> 알토큰 + 유저고유번호 쿠키시간 동일
				$.cookie('aaToken',data.data.aaToken,{ expires: aatokenDate,path: 'http://localhost:8080/'});
				$.cookie('rrToken',data.data.rrToken,{ expires: rrtokenDate,path: 'http://localhost:8080/'});
				$.cookie('userNum',data.data.userNum,{ expires: rrtokenDate,path: 'http://localhost:8080/'});
				console.log("쿠키저장함");
				window.location='/common/main';
			    
			  })
			  .catch(function (error) {
			    console.log(error);
			  });  
		/*
		axios.post('http://localhost:8080/api/login', {
            userId: 'java01',
            userPw: 'java01'
			  })
			  .then(function (data) {

				var atokenDate = new Date();
				var rtokenDate = new Date();
				atokenDate.setTime(atokenDate.getTime() + 1*10*1000); // 중앙값 60으로바꾸면 1분
				rtokenDate.setTime(rtokenDate.getTime() + 1*60*1000); // 중앙값 60으로바꾸면 1분
				$.cookie('aToken',data.data.aToken,{ expires: atokenDate,path: 'http://localhost:8080/'});
				$.cookie('rToken',data.data.rToken,{ expires: rtokenDate,path: 'http://localhost:8080/'});
				console.log("쿠키저장함");
				window.location='/common/main';
			    
			  })
			  .catch(function (error) {
			    console.log(error);
			  });   		
		*/
    	/*
		$.ajax({ //jquery ajax
		    type:"post", //get방식으로 가져오기
		    url:"http://localhost:8080/common/login", //값을 가져올 경로
		    contentType: "application/json; charset=utf-8",
		    data : JSON.stringify(vo),
		    success: function(data){   //요청 성공시 실행될 메서드
		        console.log("통신성공");
		        console.log(data);
		    },
		    error:function(){		 //요청 실패시 에러 확인을 위함
		        console.log("통신에러");
		    }
		})
		*/
	}); // $("#btn").on("click",function(){
}); //$(document).ready(function(){

</script>
</body>
</html>