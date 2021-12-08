package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;

public interface InterestMapMapper {
	List<HouseInfoDto> getCafe(String gugunCode) throws SQLException;

	List<HouseInfoDto> getFastFood(String gugunCode) throws SQLException;

	List<HouseInfoDto> getSchool(String gugunCode) throws SQLException;

	List<HouseInfoDto> getCorona(String gugunCode) throws SQLException;

	List<HouseInfoDto> getHospital(String gugunCode) throws SQLException;
}
