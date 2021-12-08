//package com.ssafy.happyhouse.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.happyhouse.model.service.BoardService;
//import com.ssafy.util.PageNavigation;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//
////http://localhost/swagger-ui.html#/
////@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
//@RestController
//@RequestMapping("/board")
//@Api("게시판 컨트롤러  API V1")
//public class PageController {
//
//	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
//	private static final String SUCCESS = "success";
//	private static final String FAIL = "fail";
//
//	@Autowired
//	private BoardService boardService;
//
//	@ApiOperation(value = "게시판 질문작성", notes = "질문 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PostMapping
//	public ResponseEntity<String> writeArticle(
//			@RequestBody @ApiParam(value = "게시글 정보.", required = true) PageNavigation pagenavigation) throws Exception {
//		logger.info("writeArticle - 호출");
//		if (boardService.(pagenavigation)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//
//}