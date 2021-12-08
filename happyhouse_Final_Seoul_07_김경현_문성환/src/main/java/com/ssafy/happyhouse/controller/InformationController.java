package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.InformationDto;
import com.ssafy.happyhouse.model.service.InformationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@Api("공지 컨트롤러  API V1")
public class InformationController {

	private static final Logger logger = LoggerFactory.getLogger(BoardQnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	InformationService informationService;

	@ApiOperation(value = "공지 리스트 반환", notes = "공지 리스트의 여부에 따라 리스트와 OK 또는 NO_CONTENT를 반환함", response = List.class)
	@GetMapping(value = "/info")
	public ResponseEntity<List<InformationDto>> infoList() throws Exception {
		logger.info("infoList - 호출");
		List<InformationDto> list = informationService.infoList();
		return new ResponseEntity<List<InformationDto>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "공지 작성", notes = "공지를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value = "/info")
	public ResponseEntity<String> infoReg(
			@ApiParam(value = "공지 정보.", required = true) @RequestBody InformationDto informationDto) throws Exception {
		logger.info("infoReg - 호출");
		if (informationService.infoReg(informationDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "공지 검색", notes = "검색어 입력하면 제목에 검색어가 포함된 리스트를 반환한다. 없다면 NO_CONTENT를 반환한다.", response = List.class)
	@PostMapping(value = "/info/search")
	public ResponseEntity<List<InformationDto>> InfoSearch(
			@ApiParam(value = "검색어.", required = false) @RequestParam("subject") String subject,
			@ApiParam(value = "검색어.", required = false) @RequestParam("currentpage") int currentpage) throws Exception {

		logger.info("infoSearch - 호출");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subject", subject);
		map.put("currentpage", currentpage);
		int sizePerPage = 5;
		int start = (currentpage - 1) * sizePerPage;
		map.put("start", start);
		map.put("spp", sizePerPage);
		return new ResponseEntity<List<InformationDto>>(informationService.infoSearch(map), HttpStatus.OK);
	}

	@ApiOperation(value = "공지갯수", notes = "검색어가 포함된 공지의 갯수를 반환한다.", response = List.class)
	@GetMapping(value = "/info/count")
	public ResponseEntity<Integer> count(
			@ApiParam(value = "게시글을 얻기위한 검색어.", required = false) @RequestParam String subject) throws Exception {
		if (subject == null)
			subject = "";
		logger.info("listArticle - 호출");
		return new ResponseEntity<Integer>(informationService.count(subject), HttpStatus.OK);
	}

	@ApiOperation(value = "공지 수정", notes = "공지를 수정한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping(value = "/info")
	public ResponseEntity<String> infoUpdate(
			@ApiParam(value = "게시글 수정 정보.", required = true) @RequestBody InformationDto informationDto)
			throws Exception {
		logger.info("infoUpdate - 호출");
		if (informationService.infoupdate(informationDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "공지 선택", notes = "번호를 입력하면 번호에 해당하는 공지를 반환한다.", response = InformationDto.class)
	@GetMapping(value = "/info/get/{num}")
	public ResponseEntity<InformationDto> infoGet(
			@ApiParam(value = "공지 번호.", required = true) @PathVariable("num") String num) throws Exception {
		System.out.println(num);
		logger.info("infoGet - 호출");
		InformationDto information = informationService.infoGet(num);
		return new ResponseEntity<InformationDto>(information, HttpStatus.OK);
	}

	@ApiOperation(value = "공지 삭제", notes = "번호를 입력하면 번호에 해당하는 공지를 삭제한다. 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping(value = "/info/{num}")
	public ResponseEntity<String> userDelete(
			@ApiParam(value = "공지 번호.", required = true) @PathVariable("num") String num) throws Exception {
		logger.info("infoDelete - 호출");
		if (informationService.infoDel(num)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
