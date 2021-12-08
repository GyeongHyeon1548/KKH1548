package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.BoardQnaDto;
import com.ssafy.happyhouse.model.mapper.BoardQnaMapper;

@Service
public class BoardQnaServiceImpl implements BoardQnaService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeQna(BoardQnaDto boardQnaDto) throws Exception {
		if (boardQnaDto.getSubject() == null || boardQnaDto.getContent() == null) {
			throw new Exception();
		}
		sqlSession.getMapper(BoardQnaMapper.class).checkQna(boardQnaDto.getTargetno());
		return sqlSession.getMapper(BoardQnaMapper.class).writeQna(boardQnaDto) == 1;
	}

	@Override
	public List<BoardQnaDto> listQna(int targetno) throws SQLException {
		return sqlSession.getMapper(BoardQnaMapper.class).listQna(targetno);
	}

	@Override
	public boolean modifyQna(BoardQnaDto boardQnaDto) throws SQLException {
		return sqlSession.getMapper(BoardQnaMapper.class).modifyQna(boardQnaDto) == 1;
	}

	@Override
	public boolean deleteQna(int articleno) throws SQLException {
		sqlSession.getMapper(BoardQnaMapper.class).uncheckQna(articleno);
		return sqlSession.getMapper(BoardQnaMapper.class).deleteQna(articleno) == 1;
	}

	public boolean deleteAllQna(int targetno) throws SQLException {
		return sqlSession.getMapper(BoardQnaMapper.class).deleteAllQna(targetno) == 1;
	}
}
