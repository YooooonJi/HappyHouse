package com.ssafy.happyhouse.model;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
	private int boardId;
	private int userSeq;
	private String userName;
	private String title;
	private String content;
	private LocalDateTime regDt;
	private int readCount;
	
	private List<BoardFileDto> fileList;
	
	public BoardDto() {}
	public BoardDto(int userSeq, String title, String content) {
		super();
		this.userSeq = userSeq;
		this.title = title;
		this.content = content;
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegDt() {
		return regDt;
	}
	public void setRegDt(LocalDateTime regDt) {
		this.regDt = regDt;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public List<BoardFileDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<BoardFileDto> fileList) {
		this.fileList = fileList;
	}
	
	
}
