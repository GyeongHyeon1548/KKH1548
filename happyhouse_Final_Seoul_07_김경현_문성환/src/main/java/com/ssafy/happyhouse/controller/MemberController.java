package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.MemberInterestDto;
import com.ssafy.happyhouse.model.SocialMemberDto;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/users")
public class MemberController extends HttpServlet {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "질문 답변 작성수 검색", notes = "해당 id의 질문 답변 작성 수를 배열로 반환, 배열의 첫번째 값은 질문수, 두번째 값은 답변수", response = String[].class)
	@GetMapping(value = "/articleCount/{userId}")
	public ResponseEntity<String[]> membercount(
			@ApiParam(value = "검색시 필요한 회원정보.", required = true) @PathVariable("userId") String userId)
			throws Exception {

		return new ResponseEntity<String[]>(memberService.membercount(userId), HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보삭제", notes = "회원정보를 삭제한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@DeleteMapping()
	public ResponseEntity<String> delete(
			@ApiParam(value = "삭제시 필요한 회원정보.", required = true) @RequestBody MemberDto memberDto) throws Exception {
		if (memberService.deleteMember(memberDto.getUserId())) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@PutMapping()
	public ResponseEntity<String> update(
			@ApiParam(value = "수정시 필요한 회원정보.", required = true) @RequestBody MemberDto memberDto) throws Exception {
		if (memberService.updateMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "인증정보수정", notes = "인증정보를 수정한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@PutMapping("/certify")
	public ResponseEntity<String> Certify(
			@ApiParam(value = "인증시 필요한 회원정보.", required = true) @RequestBody MemberDto memberDto) throws Exception {
		if (memberService.certifyMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원정보 등록", notes = "회원정보를 등록한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@PostMapping()
	public ResponseEntity<String> register(
			@ApiParam(value = "등록시 필요한 회원정보.", required = true) @RequestBody MemberDto memberDto) throws Exception {
		if (memberService.registerMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "소셜 회원정보 등록", notes = "소셜 회원정보를 등록한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@PostMapping("/social")
	public ResponseEntity<String> registerSocial(
			@ApiParam(value = "등록시 필요한 회원정보.", required = true) @RequestBody SocialMemberDto socialMemberDto)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", socialMemberDto.getEmail());
		map.put("username", socialMemberDto.getUserName());
		map.put("userpwd", socialMemberDto.getUserPwd());
		map.put("id", socialMemberDto.getId());

		if (memberService.registerMemberSocial(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "소셜로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@GetMapping("/social/login/{id}")
	public ResponseEntity<Map<String, Object>> loginSocial(
			@ApiParam(value = "소셜로그인 시 필요한 회원정보(이메일).", required = true) @PathVariable("id") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.loginSocial(id);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "관심사등록", notes = "관심사를 등록한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@PutMapping("/interest")
	public ResponseEntity<String> registerInterest(
			@ApiParam(value = "등록시 필요한 회원정보.", required = true) @RequestBody MemberInterestDto memberInterestDto)
			throws Exception {
		System.out.println(memberInterestDto);
		if (memberService.registerInterest(memberInterestDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "관심사 삭제", notes = "관심사를 삭제한다. 성공시 OK, 실패시 Fail을 반환", response = String.class)
	@DeleteMapping("/interest")
	public ResponseEntity<String> deleteInterest(
			@ApiParam(value = "!등록시 필요한 회원정보.", required = true) @RequestBody MemberInterestDto memberInterestDto)
			throws Exception {
		System.out.println(memberInterestDto.toString());
		if (memberService.deleteInterest(memberInterestDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "관심사 목록", notes = "관심사를 반환한다.", response = List.class)
	@GetMapping("/interest/{userid}")
	public ResponseEntity<List<MemberInterestDto>> GetInterest(
			@ApiParam(value = "등록시 필요한 회원정보.", required = true) @PathVariable("userid") String userid)
			throws Exception {

		List<MemberInterestDto> list = memberService.listInterest(userid);
		System.out.println(list);
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberInterestDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

	}

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원목록", notes = "회원의 리스트를 반환, 없을시 NO_CONTENT를 반환", response = List.class)
	@GetMapping(value = "/list")
	public ResponseEntity<List<MemberDto>> listMember() throws Exception {
		List<MemberDto> list = memberService.listMember();
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "회원검색", notes = "회원을 이름으로 검색한 리스트를 반환, 없을시 NO_CONTENT를 반환", response = List.class)
	@GetMapping(value = "/list/name/{name}")
	public ResponseEntity<List<MemberDto>> searchMember(@PathVariable("name") String name) throws Exception {
		List<MemberDto> list = memberService.searchMember(name);
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "회원검색", notes = "회원을 ID으로 검색한 리스트를 반환, 없을시 NO_CONTENT를 반환", response = List.class)
	@GetMapping(value = "/list/id/{userid}")
	public ResponseEntity<MemberDto> searchMemberbyId(@PathVariable("userid") String userid) throws Exception {
		MemberDto memberDto = memberService.searchMemberById(userid);
		if (memberDto != null) {
			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

}
