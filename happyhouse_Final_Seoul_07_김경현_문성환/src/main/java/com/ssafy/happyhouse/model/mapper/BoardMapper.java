package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardDto;

@Mapper
public interface BoardMapper {
	public int writeArticle(BoardDto boardDto) throws SQLException;

	public List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;

	public int getTotalCountQna() throws SQLException;

	public BoardDto getArticle(int articleno) throws SQLException;

	public void updateHit(int articleno) throws SQLException;

	public int modifyArticle(BoardDto boardDto) throws SQLException;

	public void deleteMemo(int articleno) throws SQLException;

	public int deleteArticle(int articleno) throws SQLException;

}