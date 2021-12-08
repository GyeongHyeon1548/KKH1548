package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDto;

public interface BoardService {
	public boolean writeArticle(BoardDto boardDto) throws Exception;

	public List<BoardDto> listArticle(String subject, int currentPage) throws Exception;

	public int qcount(String subject) throws Exception;

//	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;

	public BoardDto getArticle(int articleno) throws Exception;

	public void updateHit(int articleno) throws Exception;

	public boolean modifyArticle(BoardDto boardDto) throws Exception;

	public boolean deleteArticle(int articleno) throws Exception;
}
