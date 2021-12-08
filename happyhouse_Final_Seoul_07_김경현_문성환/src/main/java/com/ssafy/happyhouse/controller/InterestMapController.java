package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.service.InterestMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost/swagger-ui.html#/
@RestController
@RequestMapping("/map/interest")
@Api("주소 컨트롤러  API V1")
public class InterestMapController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private InterestMapService InterestMapService;

	@ApiOperation(value = "카페 리스트 반환", notes = "카페 리스트 코드를 반환한다.", response = List.class)
	@GetMapping("/cafe")
	public ResponseEntity<List<HouseInfoDto>> getcafe(
			@ApiParam(value = "구군코드.", required = true) @RequestParam("gugunCode") String gugunCode) throws Exception {
		logger.info("cafe - 호출");

		return new ResponseEntity<List<HouseInfoDto>>(InterestMapService.getCafe(gugunCode), HttpStatus.OK);
	}

	@ApiOperation(value = "패스트푸드 리스트 반환", notes = "패스트푸드 정보를 반환한다.", response = List.class)
	@GetMapping("/fastfood")
	public ResponseEntity<List<HouseInfoDto>> getfastfood(
			@ApiParam(value = "구군코드.", required = true) @RequestParam("gugunCode") String gugunCode) throws Exception {
		logger.info("fastfood - 호출");

		return new ResponseEntity<List<HouseInfoDto>>(InterestMapService.getFastFood(gugunCode), HttpStatus.OK);
	}

	@ApiOperation(value = "학교 리스트 반환", notes = "학교 정보를 반환한다.", response = List.class)
	@GetMapping("/school")
	public ResponseEntity<List<HouseInfoDto>> getSchool(
			@ApiParam(value = "구군코드.", required = true) @RequestParam("gugunCode") String gugunCode) throws Exception {
		logger.info("fastfood - 호출");

		return new ResponseEntity<List<HouseInfoDto>>(InterestMapService.getSchool(gugunCode), HttpStatus.OK);
	}

	@ApiOperation(value = "코로나 선별진료소 리스트 반환", notes = "코로나 선별 진료소 정보를 반환한다.", response = List.class)
	@GetMapping("/corona")
	public ResponseEntity<List<HouseInfoDto>> getCorona(
			@ApiParam(value = "구군코드.", required = true) @RequestParam("gugunCode") String gugunCode) throws Exception {
		logger.info("cafe - 호출");

		return new ResponseEntity<List<HouseInfoDto>>(InterestMapService.getCorona(gugunCode), HttpStatus.OK);
	}

	@ApiOperation(value = "안심병원 리스트 반환", notes = "안심병원 정보를 반환한다.", response = List.class)
	@GetMapping("/hospital")
	public ResponseEntity<List<HouseInfoDto>> gethospital(
			@ApiParam(value = "구군코드.", required = true) @RequestParam("gugunCode") String gugunCode) throws Exception {
		logger.info("cafe - 호출");

		return new ResponseEntity<List<HouseInfoDto>>(InterestMapService.getHospital(gugunCode), HttpStatus.OK);
	}
}
