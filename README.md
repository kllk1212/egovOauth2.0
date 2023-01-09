# egovOauth2.0



oauth2.0 사이트 자체 로그인 구현 

-- 메인 페이지
http://localhost:8080/common/main

-- 회원가입
http://localhost:8080/common/signup


1. 회원가입시 egovOauthMember 테이블에 회원정보 저장 + tokens 테이블에 유저고유번호 저장 (access_token,refresh_token은 null로 저장 )테이블 2개에 저장

2. 로그인시 아이디 비밀번호 맞을 경우 egovOauthMember 테이블에 userId를 주고 userNum을 가져옴 , 가져온 userNum으로 tokens 테이블에 생성된 access_token,refresh_token 저장
	http://localhost:8080/common/main 에서 허공에 마우스를 클릭 할경우 access_token 이 만료되지 않았는지(쿠키에 access_token 있는지 없는지 ) 확인 후 만료시(없을시) 컨트롤러에
	refresh_token을 주고 새로운 access_token 생성후 쿠키에 저장 + token 테이블에 access_token을 update
	(다시 허공에 마우스 클릭시 console로 확인가능)
	


3. 로그아웃시 tokens 테이블에 access_token,refresh_token null로 update



----------------------------------------------------------------------------------------------------------------
* 토큰생성 - 토큰생성식 = 서버시크릿키 + 현재 시스템시간 + 숫자
(실제로는 엄청 복잡한 코드로 토큰값들을 변환하지만 은행사이트를 만들껀 아니니까 이정도면 될듯 간소화)

    access_token  - > aaToken - 10초뒤 만료
    refresh_token - > rrToken - 10분뒤 만료

* 프론트에서 컨트롤러로 데이터 보낼때 axios사용 json 데이터로 보냄

* <script type="text/javascript" src="/js/jquery.cookie.js"></script> 
	쿠키 생성시 제이쿼리 쿠키 사용 파일 다운받아서 src 경로에 넣어야함

-----------------------------------------------------------------------------------------------------------------

// 유저고유번호 저장위한 시퀀스
create sequence egovOauthMember_seq nocache; -- egovOauthMember_seq.nextval,


// 회원정보 테이블
create table egovOauthMember(
    userNum number unique not null,	-- 유저 고유번호
    userId varchar2(50) primary key,	--  아이디
    userPw varchar2(100),               -- 비밀번호
    nickName varchar2(50) unique not null, -- 닉네임			
    userName varchar2(50),		-- 이름
    email varchar2(50),			-- 이메일
    gender varchar2(50),			-- 성별
    reg TIMESTAMP DEFAULT sysdate,	-- 가입시간
    enabled varchar2(50) DEFAULT 1,	-- 회원상태 0정지
    sns varchar2(50) default 'site' 	-- 가입루트 ( 사이트 / 네이버/ 카카오 ) 

);

// 토큰 테이블
create table tokens(
    userNum number,		-- 유저고유번호
    aaToken varchar2(200),	-- 엑세스 토큰
    rrToken varchar2(200)		-- 리플레쉬 토큰
);

	<!-- 
	<bean id="dataSource"
		class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://아이피" />
		<property name="username" value="root" />
		<property name="password" value="java11" />
	</bean>
    
    <!-- oracle (POM에서 commons-dbcp, ojdbc(라이센스 사항으로 별도로 배포되지 않음) 관련 라이브러리 설정)
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
        <property name="url" value="jdbc:oracle:thin:@아이피:ORCL"/>
        <property name="username" value="leetest"/>
        <property name="password" value="java11"/>
    </bean>

