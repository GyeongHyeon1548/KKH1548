package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.MemberInterestDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getUserId() == null || memberDto.getUserPwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto loginSocial(String userid) throws Exception {
		if (userid == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).loginSocial(userid);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public boolean registerMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).registerMember(memberDto) == 1;
	}

	@Override
	public boolean registerMemberSocial(Map<String, String> map) throws Exception {
		MemberDto memberDto = new MemberDto();
		memberDto.setUserName(map.get("username"));
		memberDto.setUserPwd(map.get("userpwd"));
		memberDto.setUserId(map.get("id"));
		System.out.println("===================================" + map.toString());
		if (sqlSession.getMapper(MemberMapper.class).registerMemberSocial(map) == 1)
			return sqlSession.getMapper(MemberMapper.class).registerMember(memberDto) == 1;
		else
			return false;
	}

	@Override
	public boolean updateMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).updateMember(memberDto) == 1;

	}

	public boolean certifyMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).certifyMember(memberDto) == 1;
	}

	@Override
	public boolean deleteMember(String id) throws Exception {
		sqlSession.getMapper(MemberMapper.class).deleteMemberSocial(id);
		return sqlSession.getMapper(MemberMapper.class).deleteMember(id) == 1;
	}

	@Override
	public List<MemberDto> listMember() throws Exception {
		return sqlSession.getMapper(MemberMapper.class).listMember();
	}

	@Override
	public List<MemberInterestDto> listInterest(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).listInterest(userid);
	}

	@Override
	public List<MemberDto> searchMember(String name) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).searchMember(name);
	}

	@Override
	public MemberDto searchMemberById(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).searchMemberById(userid);
	}

	@Override
	public boolean registerInterest(MemberInterestDto memberInterestDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).registerInterest(memberInterestDto) == 1;
	}

	@Override
	public boolean deleteInterest(MemberInterestDto memberInterestDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteInterest(memberInterestDto) == 1;
	}

	public String[] membercount(String userid) throws Exception {
		String[] count = { "", "" };

		count[0] = sqlSession.getMapper(MemberMapper.class).Qcount(userid);
		count[1] = sqlSession.getMapper(MemberMapper.class).Acount(userid);

		return count;
	}

}
