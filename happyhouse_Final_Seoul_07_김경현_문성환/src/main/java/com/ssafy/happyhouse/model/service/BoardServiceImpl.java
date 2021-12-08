package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.mapper.BoardMapper;
import com.ssafy.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeArticle(BoardDto boardDto) throws Exception {
		if (boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeArticle(boardDto) == 1;
	}

	@Override
	public int qcount(String subject) throws Exception {

		return sqlSession.getMapper(BoardMapper.class).getTotalCountQna();
	}

	@Override
	public List<BoardDto> listArticle(String subject, int currentPage) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCountQna();// 총글갯수 269
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / 5 + 1;// 27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);

		Map<String, Object> param = new HashMap<String, Object>();

		int sizePerPage = 5;
		int start = (currentPage - 1) * sizePerPage;
		param.put("start", start);
		param.put("spp", sizePerPage);
		return sqlSession.getMapper(BoardMapper.class).listArticle(param);
	}

//	@Override
//	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
//		int naviSize = 5;
//		PageNavigation pageNavigation = new PageNavigation();
//		pageNavigation.setCurrentPage(boardParameterDto.getPg());
//		pageNavigation.setNaviSize(naviSize);
//		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);// 총글갯수 269
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;// 27
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = boardParameterDto.getPg() <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//		return pageNavigation;
//	}

	@Override
	public BoardDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getArticle(articleno);
	}

	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyArticle(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno) == 1;
	}

}