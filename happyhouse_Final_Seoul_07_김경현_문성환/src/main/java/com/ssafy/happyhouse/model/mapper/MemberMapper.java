package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.MemberInterestDto;

public interface MemberMapper {

//	int idCheck(String id) throws Exception;
//
	int registerMember(MemberDto memberDto) throws SQLException;

	int registerInterest(MemberInterestDto memberInterestDto) throws Exception;

	int deleteInterest(MemberInterestDto memberInterestDto) throws Exception;

	List<MemberInterestDto> listInterest(String userid) throws Exception;

	List<MemberDto> listMember() throws Exception;

	List<MemberDto> searchMember(String name) throws Exception;

	MemberDto searchMemberById(String name) throws Exception;

	public MemberDto login(MemberDto memberDto) throws SQLException;

	public MemberDto userInfo(String userid) throws SQLException;

//	MemberDto getMember(String id) throws Exception;

	int updateMember(MemberDto memberDto) throws Exception;

	int deleteMember(String id) throws Exception;

	int deleteMemberSocial(String id) throws Exception;

	String Qcount(String userid) throws Exception;

	String Acount(String userid) throws Exception;

	int registerMemberSocial(Map<String, String> map) throws SQLException;

	MemberDto loginSocial(String email) throws SQLException;

	int certifyMember(MemberDto memberDto) throws SQLException;
}
