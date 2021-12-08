package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardQnaDto;

@Mapper
public interface BoardQnaMapper {

	public int writeQna(BoardQnaDto boardQnaDto) throws SQLException;

	public List<BoardQnaDto> listQna(int targetno) throws SQLException;

	public int modifyQna(BoardQnaDto boardQnaDto) throws SQLException;

	public int deleteQna(int articleno) throws SQLException;

	public int deleteAllQna(int targetno) throws SQLException;

	public int checkQna(int targetno) throws SQLException;

	public int uncheckQna(int articleno) throws SQLException;

}