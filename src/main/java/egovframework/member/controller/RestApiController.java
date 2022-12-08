package egovframework.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import egovframework.member.service.MemberService;
import egovframework.member.vo.MemberVO;
import egovframework.member.vo.Tokens;

@RestController
@RequestMapping("/api/*")
public class RestApiController {

	private String key = "ddddddasdasd";
	 
	@Autowired
	private MemberService service;
	
	@ResponseBody
	@PostMapping(value="login",consumes = "application/json"
			, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ModelAndView loginPro(@RequestBody MemberVO vo,HttpServletResponse response) {
		System.out.println("넘어온 VO : " + vo);
		System.out.println(vo.getUserId());
	    ModelAndView mv = new ModelAndView("jsonView");
	    mv.addObject("result","성공");
		int total = service.allCheck(vo); // 0 아이디비번틀림 / 1 활동중/ 2 정지중
		if(total == 0) { // 아이디 비번틀림  000
			mv.addObject("result", "000 / (아이디비밀번호오입력)");
		}else if(total == 1) { // 아이디 비번 맞음 001
			// 1. 토큰생성
			String aaToken = vo.getUserId() + "."+Long.toString(System.currentTimeMillis() + 300000); // 5 * 60 * 1000 5분
			String rrToken = vo.getUserId() + "." + Long.toString(System.currentTimeMillis() + 600000); // 10분
			// 2. 유저고유번호가져오기
			int userNum = service.getUserNum(vo);
			// 3. tokens 객체 생성 후 set
			Tokens tokens = new Tokens();
			tokens.setAaToken(aaToken);
			tokens.setRrToken(rrToken);
			tokens.setUserNum(userNum);
			// 4. 가져온 유저 고유번호로 토큰 update
			int updateTokens = service.updateTokens(tokens);
			mv.addObject("result", "001 / (아이디비번맞음 활동중)");
			mv.addObject("userNum", userNum);
			mv.addObject("aaToken", aaToken);
			mv.addObject("rrToken", rrToken);
		}else if(total == 2) { // 아이디 비번 맞지만 정지중임 002
			mv.addObject("result", "002 / (회원정지중)");
		}
	    return mv;
	}// PostMapping("login")	
	
	

	
	// 회원가입 처리 
	@PostMapping("signup")
	public ModelAndView signupPro(MemberVO member) {
		System.out.println("*************************************************");
		System.out.println(member);
		int result = service.addMember(member);  // 회원테이블 추가 
		System.out.println("회원추가 : " + result);
		ModelAndView mav = new ModelAndView();
		if(result == 1) { // 회원추가성공
			// 회원 고유번호 찾아오기
			int userNum = service.getUserNum(member);
			// 토큰 테이블 생성
			int tokenTable = service.addTokens(userNum);
			mav.setViewName("redirect:/common/main");
			return mav;
			
		}else { //회원추가실패
			mav.setViewName("redirect:/common/signup");
			return mav;
		}
	}//signup

	@PostMapping(value="logout",consumes = "application/json"
			, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ModelAndView logout(@RequestBody Tokens tokens) {
		//쿠키에 저장된 토큰 db 삭제 
		System.out.println("로그아웃 컨트롤러로 넘어온 데이터 : " + tokens);
		ModelAndView mav = new ModelAndView("jsonView");
		int result = service.deleteTokens(tokens);
		
		
		//mav.setViewName("redirect:/common/main");
		return mav;
	}
	
	@ResponseBody
	@PostMapping(value="aTokenCreate",consumes = "application/json"
			, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ModelAndView aatoken(@RequestBody Tokens tokens) {
		ModelAndView mav = new ModelAndView("jsonView");
		System.out.println("tokens : " + tokens);
		//DB에다가 알토큰을 주면 회원번호를 가져오고 회원번호가져온걸로 회원DB에서 아이디 가져와야함
		// 1. 토큰테이블에 알토큰 주고 회원번호 가져오기
		int getUserNumAtTokenDB = service.getUserNumAtTokenDB(tokens);
		System.out.println("1. 토큰DB에서 가져온 유저고유번호 : " + getUserNumAtTokenDB);
		
		// 2. 가져온 회원번호로 회원테이블에서 아이디 가져오기
		String getUserId = service.getUserId(getUserNumAtTokenDB);
		System.out.println("2. 가져온 회원아이디 : " + getUserId);
		
		// 3. 가져온 아이디 + 토큰 생성해서 에이토큰으로 담기 
		String aaToken = getUserId + "."+Long.toString(System.currentTimeMillis() + 300000); // 5 * 60 * 1000 5분
		System.out.println("3. 다시 생성한 에이토큰 : " + aaToken);
		
		
		// 4. 에이토큰 토큰테이블에 저장 
		Tokens reToken = new Tokens();
		reToken.setAaToken(aaToken);
		reToken.setUserNum(getUserNumAtTokenDB);
		service.saveAaToken(reToken);
		
		// 5 생성한 에이토큰 뷰페이지로 리턴 . 
		mav.addObject("aaToken", aaToken);
		
		//String aToken = vo.getUserId() + "."+Long.toString(System.currentTimeMillis() + 300000); // 5 * 60 * 1000 5분
		//Tokens tokens = new Tokens();
		//tokens.setAToken(aToken);
		//mv.addObject("result","aatoken 재생성 성공");
		return mav;
	}
		
	
	
}
