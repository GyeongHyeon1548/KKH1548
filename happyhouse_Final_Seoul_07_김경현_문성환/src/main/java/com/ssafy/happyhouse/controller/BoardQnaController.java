package com.ssafy.happyhouse.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BoardQnaDto;
import com.ssafy.happyhouse.model.service.BoardQnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost/swagger-ui.html/
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/Qna")
@Api("답변 컨트롤러  API V1")
public class BoardQnaController {

	private static final Logger logger = LoggerFactory.getLogger(BoardQnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardQnaService boardQnaService;

	@ApiOperation(value = "답변작성", notes = "답변 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeQna(
			@RequestBody @ApiParam(value = "답변 정보.", required = true) BoardQnaDto boardQnaDto) throws Exception {
		logger.info("writeQna - 호출");
		if (boardQnaService.writeQna(boardQnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 답변 목록", notes = "해당 개시글의 답변들의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardQnaDto>> listQna(
			@ApiParam(value = "답변을 얻기위한 질문번호.", required = true) @RequestParam("targetno") int targetno)
			throws Exception {
		logger.info("listQna - 호출");
		return new ResponseEntity<List<BoardQnaDto>>(boardQnaService.listQna(targetno), HttpStatus.OK);
	}

	@ApiOperation(value = "답변 수정", notes = "새로운 답변 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyQna(
			@RequestBody @ApiParam(value = "수정할 답변정보.", required = true) BoardQnaDto boardQnaDto) throws Exception {
		logger.info("modifyQna - 호출");

		if (boardQnaService.modifyQna(boardQnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "답변 삭제", notes = "해당하는 답변의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteQna(
			@PathVariable("articleno") @ApiParam(value = "삭제할 답변의 번호.", required = true) int articleno)
			throws Exception {
		logger.info("deleteQna - 호출");
		if (boardQnaService.deleteQna(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "답변 전체 삭제", notes = "해당하는 답변의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/all/{targetno}")
	public ResponseEntity<String> deleteAllQna(
			@PathVariable("targetno") @ApiParam(value = "삭제할 답변의 번호.", required = true) int targetno) throws Exception {
		logger.info("deleteQna - 호출");
		if (boardQnaService.deleteAllQna(targetno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}