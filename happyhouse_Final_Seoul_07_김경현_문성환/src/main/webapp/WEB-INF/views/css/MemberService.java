package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.model.MemberDto;

public interface MemberService {

	int idCheck(String id) throws Exception;

	void registerMember(MemberDto memberDto) throws Exception;

	MemberDto login(String id, String pass) throws Exception;

//	MemberDto getMember(String id) throws Exception;
	void updateMember(MemberDto memberDto, String id) throws Exception;

	void deleteMember(String id) throws Exception;

}
