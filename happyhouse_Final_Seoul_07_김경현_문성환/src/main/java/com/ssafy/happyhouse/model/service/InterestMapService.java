package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;

public interface InterestMapService {
	List<HouseInfoDto> getCafe(String gugunCode) throws Exception;

	List<HouseInfoDto> getFastFood(String gugunCode) throws Exception;

	List<HouseInfoDto> getSchool(String gugunCode) throws Exception;

	List<HouseInfoDto> getCorona(String gugunCode) throws Exception;

	List<HouseInfoDto> getHospital(String gugunCode) throws Exception;
}
