package egovframework.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.member.mapper.MemberMapper;
import egovframework.member.vo.MemberVO;
import egovframework.member.vo.Tokens;




@Service
public class MemberServiceImpl implements MemberService{
	private static final String SECRET_KEY = "aasjjkjaskjdl1k2naskjkdakj34c8sa";

	@Autowired
	private MemberMapper mapper;
	/*
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	*/
	@Override
	public int addMember(MemberVO vo) {

		return mapper.addMember(vo);
	}
	@Override
	public int idpwChk(MemberVO vo) {

		return mapper.idpwChk(vo);
	}

	@Override
	public int memberChk(MemberVO vo) {
		return mapper.memberChk(vo);
	}
	@Override
	public int saveToken(Tokens token) {
		return mapper.saveToken(token);
	}
	
	@Override
	public int allCheck(MemberVO vo) {
		int idPwChk = mapper.idpwChk(vo); // 1 아이디 비번맞음 0 아이디 비번틀림
		
		if(idPwChk == 1) { // 아이디 비번이 맞을경우 - > 정지중인지 체크 0정지 1활동중
			int enabled = mapper.memberChk(vo); 
			if(enabled == 0) { // 아이디 비번이 맞지만 정지중임   -> 2
				return 2;
			}else if(enabled == 1){ // 아이디 비번 맞고 활동중임   ->1
				return 1;
			}
		}else {	// 아이디 비번이 틀릴 경우						->0
			return 0;
		}
		return 0;
	}
	@Override
	public int getUserNum(MemberVO vo) {
		
		return mapper.getUserNum(vo);
	}
	@Override
	public int saveTokensNum(Tokens token) {
		return mapper.saveTokensNum(token);
	}
	@Override
	public int tokenFind(int userNum) {
		return mapper.tokenFind(userNum);
	}
	@Override
	public int addTokens(int userNum) {
		// TODO Auto-generated method stub
		return mapper.addTokens(userNum);
	}
	@Override
	public int updateTokens(Tokens token) {
		return mapper.updateTokens(token);
	}
	@Override
	public int deleteTokens(Tokens token) {
		// TODO Auto-generated method stub
		return mapper.deleteTokens(token);
	}
	@Override
	public int getUserNumAtTokenDB(Tokens tokens) {
		// TODO Auto-generated method stub
		return mapper.getUserNumAtTokenDB(tokens);
	}
	@Override
	public String getUserId(int getUserNumAtTokenDB) {
		// TODO Auto-generated method stub
		return mapper.getUserId(getUserNumAtTokenDB);
	}
	@Override
	public void saveAaToken(Tokens reTokens) {
		mapper.saveAaToken(reTokens);
	}



	

}
