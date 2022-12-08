package egovframework.member.service;

import egovframework.member.vo.MemberVO;
import egovframework.member.vo.Tokens;

public interface MemberService {

	
	public int addMember(MemberVO vo); // 회원가입
	public int addTokens(int userNum); // 회원가입 성공시 토큰테이블 생성 
	public int updateTokens(Tokens token); // 로그인시 토큰 업데이트
	
	public int deleteTokens(Tokens token); // 로그아웃시 토큰 삭제
	public int getUserNumAtTokenDB(Tokens tokens); // 에이토큰 만료시 알토큰주고 회원 고유번호 가져오는 기능
	public String getUserId(int getUserNumAtTokenDB); // 에이토큰 만료시 회원고유번호로 유저 아이디 가져오는 기능
	public void saveAaToken(Tokens reTokens);	// 에이토큰 만료시 회원고유번호 + 에이토큰 을 다시 토큰DB에 넣는 기능
	
	
	public int idpwChk(MemberVO vo); // 아이디 비밀번호 확인(로그인)
	
	public int memberChk(MemberVO vo); // 정지중인지 확인
	
	public int saveToken(Tokens token); // 토큰 저장 2개 
	public int saveTokensNum(Tokens token); // 토큰 저장 2개 + 유저고유번호
	
	public int allCheck(MemberVO vo); // 아이디 비밀번호 체크 + 정지중인지 확인 
	
	public int getUserNum(MemberVO vo); // 회원 고유번호 DB에서 가져오기
	
	public int tokenFind(int userNum); // 유저Num 주고 저장된 토큰이 있는지 있으면 1 리턴
	
	
	

	
}
