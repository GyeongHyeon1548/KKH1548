package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.InformationDto;

public interface InformationMapper {

	List<InformationDto> infoList();

	int infoReg(InformationDto informationDto);

	int infoDel(String num);

	InformationDto infoGet(String num);

	int infoUpdate(InformationDto informationDto);

	List<InformationDto> infoSearch(Map<String, Object> map);

	Integer count(String subject);

}
