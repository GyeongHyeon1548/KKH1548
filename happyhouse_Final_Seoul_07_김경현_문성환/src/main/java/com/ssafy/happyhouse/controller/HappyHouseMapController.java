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
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HappyHouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost/swagger-ui.html#/
@RestController
@RequestMapping("/map")
@Api("주소 컨트롤러  API V1")
public class HappyHouseMapController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private HappyHouseMapService happyHouseMapService;

	@ApiOperation(value = "시도 리스트 반환", notes = "시도 코드를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(happyHouseMapService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 리스트 반환", notes = "입력된 시도 코드에 따라 구군 코드를 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@ApiParam(value = "시도 코드.", required = true) @RequestParam("sido") String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(happyHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}

	@ApiOperation(value = "동 리스트 반환", notes = "입력된 구군 코드에 따라 동 코드를 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(
			@ApiParam(value = "구군 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("dong - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}

	@ApiOperation(value = "아파트 매매 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 아파트 매매 리스트를 반환한다.", response = List.class)
	@GetMapping("/aptDeal")
	public ResponseEntity<List<HouseInfoDto>> aptDeal(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getAptDealInDong(dong, gugun),
				HttpStatus.OK);
	}

	@ApiOperation(value = "아파트 전월세 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 아파트 전월세 리스트를 반환한다.", response = List.class)
	@GetMapping("/aptRent")
	public ResponseEntity<List<HouseInfoDto>> aptRent(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getAptRentInDong(dong, gugun),
				HttpStatus.OK);
	}

	@ApiOperation(value = "연립다세대 매매 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 연립다세대 매매 리스트를 반환한다.", response = List.class)
	@GetMapping("/alliDeal")
	public ResponseEntity<List<HouseInfoDto>> alliDeal(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getAlliDealInDong(dong, gugun),
				HttpStatus.OK);
	}

	@ApiOperation(value = "연립다세대 매매 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 연립다세대 매매 리스트를 반환한다.", response = List.class)
	@GetMapping("/alliRent")
	public ResponseEntity<List<HouseInfoDto>> alliRent(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getAlliRentInDong(dong, gugun),
				HttpStatus.OK);
	}

	@ApiOperation(value = "오피스텔 매매 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 오피스텔 매매 리스트를 반환한다.", response = List.class)
	@GetMapping("/officeDeal")
	public ResponseEntity<List<HouseInfoDto>> officeDeal(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getOfficeDealInDong(dong, gugun),
				HttpStatus.OK);
	}

	@ApiOperation(value = "오피스텔 매매 리스트 반환", notes = "입력된 구군코드와 동이름에 따라 오피스텔 매매 리스트를 반환한다.", response = List.class)
	@GetMapping("/officeRent")
	public ResponseEntity<List<HouseInfoDto>> officeRent(
			@ApiParam(value = "동 코드.", required = true) @RequestParam("dong") String dong,
			@ApiParam(value = "동 코드.", required = true) @RequestParam("gugun") String gugun) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getOfficeRentInDong(dong, gugun),
				HttpStatus.OK);
	}

}
