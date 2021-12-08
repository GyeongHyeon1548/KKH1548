package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.dao.MemberDao;
import com.ssafy.happyhouse.model.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService {

	private static MemberService memberService = new MemberServiceImpl();
	private MemberDao memberDao;

	private MemberServiceImpl() {
		memberDao = MemberDaoImpl.getMemberDao();
	}

	public static MemberService getMemberService() {
		return memberService;
	}

	@Override
	public int idCheck(String id) throws Exception {
		return memberDao.idCheck(id); // 0 or 1
	}

	@Override
	public void registerMember(MemberDto memberDto) throws Exception {
//		validation check
		memberDao.registerMember(memberDto);
	}

	@Override
	public MemberDto login(String id, String pass) throws Exception {
		return memberDao.login(id, pass);
	}

	@Override
	public void updateMember(MemberDto memberDto, String id) throws Exception {
		memberDao.updateMember(memberDto, id);
	}

	@Override
	public void deleteMember(String id) throws Exception {
		memberDao.deleteMember(id);
	}

}
