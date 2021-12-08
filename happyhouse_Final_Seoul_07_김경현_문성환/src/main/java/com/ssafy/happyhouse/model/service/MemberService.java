package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.MemberInterestDto;

public interface MemberService {
//
//	int idCheck(String id) throws Exception;

	boolean registerMember(MemberDto memberDto) throws Exception;

	List<MemberDto> listMember() throws Exception;

	List<MemberInterestDto> listInterest(String userid) throws Exception;

	List<MemberDto> searchMember(String name) throws Exception;

	public MemberDto login(MemberDto memberDto) throws Exception;

	public MemberDto userInfo(String userid) throws Exception;

//	MemberDto getMember(String id) throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;

	boolean deleteMember(String id) throws Exception;

	public MemberDto searchMemberById(String userid) throws Exception;

	boolean registerInterest(MemberInterestDto memberInterestDto) throws Exception;

	boolean deleteInterest(MemberInterestDto memberInterestDto) throws Exception;

	String[] membercount(String userid) throws Exception;

	boolean registerMemberSocial(Map<String, String> map) throws Exception;

	MemberDto loginSocial(String userid) throws Exception;

	boolean certifyMember(MemberDto memberDto) throws Exception;
}
