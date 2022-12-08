package egovframework.member.vo;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberVO {
	private int userNum;

	private String userId; 

	private String userPw; 
	private String nickName;
	private String userName; 
	private String email; 
	private String gender; 
	private Timestamp reg; 
	private String enabled; // 활동중 "1", 비활동중 "0" 
	private String sns;		// 가입루트  ( 사이트 / 네이버/ 카카오 )

	
}