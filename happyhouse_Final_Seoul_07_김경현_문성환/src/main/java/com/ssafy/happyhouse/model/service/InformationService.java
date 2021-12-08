package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.InformationDto;

@Service
public interface InformationService {

	List<InformationDto> infoList();

	boolean infoReg(InformationDto informationDto);

	boolean infoDel(String num);

	InformationDto infoGet(String num);

	boolean infoupdate(InformationDto informationDto);

	List<InformationDto> infoSearch(Map<String, Object> map);

	Integer count(String subject);
}
