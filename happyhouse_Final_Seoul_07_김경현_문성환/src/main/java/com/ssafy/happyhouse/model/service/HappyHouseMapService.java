package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;

public interface HappyHouseMapService {
	List<SidoGugunCodeDto> getSido() throws Exception;

	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;

	List<HouseInfoDto> getAptDealInDong(String dongName, String gugunCode) throws Exception;

	List<HouseInfoDto> getAptRentInDong(String dongName, String gugunCode) throws Exception;

	List<HouseInfoDto> getAlliDealInDong(String dongName, String gugunCode) throws Exception;

	List<HouseInfoDto> getAlliRentInDong(String dongName, String gugunCode) throws Exception;

	List<HouseInfoDto> getOfficeDealInDong(String dongName, String gugunCode) throws Exception;

	List<HouseInfoDto> getOfficeRentInDong(String dongName, String gugunCode) throws Exception;
}
