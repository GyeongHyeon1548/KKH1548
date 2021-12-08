package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.InformationDto;
import com.ssafy.happyhouse.model.mapper.InformationMapper;

@Service
public class InformationServiceImpl implements InformationService {

	@Autowired
	SqlSession sqlsession;

	@Override
	public List<InformationDto> infoList() {
		return sqlsession.getMapper(InformationMapper.class).infoList();
	}

	@Override
	public boolean infoReg(InformationDto informationDto) {
		return sqlsession.getMapper(InformationMapper.class).infoReg(informationDto) == 1;
	}

	@Override
	public boolean infoDel(String num) {
		return sqlsession.getMapper(InformationMapper.class).infoDel(num) == 1;
	}

	@Override
	public InformationDto infoGet(String num) {
		return sqlsession.getMapper(InformationMapper.class).infoGet(num);
	}

	@Override
	public boolean infoupdate(InformationDto informationDto) {
		return sqlsession.getMapper(InformationMapper.class).infoUpdate(informationDto) == 1;

	}

	@Override
	public List<InformationDto> infoSearch(Map<String, Object> map) {
		return sqlsession.getMapper(InformationMapper.class).infoSearch(map);
	}

	@Override
	public Integer count(String subject) {
		return sqlsession.getMapper(InformationMapper.class).count(subject);
	}

}
