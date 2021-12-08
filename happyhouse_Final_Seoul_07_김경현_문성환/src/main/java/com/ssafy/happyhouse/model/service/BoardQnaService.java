package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardQnaDto;

public interface BoardQnaService {
	public boolean writeQna(BoardQnaDto boardQnaDto) throws Exception;

	public List<BoardQnaDto> listQna(int targetno) throws Exception;

	public boolean modifyQna(BoardQnaDto boardQnaDto) throws Exception;

	public boolean deleteQna(int articleno) throws Exception;

	public boolean deleteAllQna(int targetno) throws Exception;
}
