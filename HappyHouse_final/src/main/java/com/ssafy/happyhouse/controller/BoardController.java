package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.BoardParamDto;
import com.ssafy.happyhouse.model.BoardResultDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.BoardService;

@RestController
public class BoardController {

	@Autowired
	BoardService service;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@PostMapping(value="/boardlist")
	private ResponseEntity<BoardResultDto> boardList(@RequestBody String params) throws Exception {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		BoardParamDto boardParamDto = new BoardParamDto();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonOffset = objectMapper.readTree(params).findPath("offset");
		int offset = jsonOffset.asInt();
		JsonNode jsonSearchWord = objectMapper.readTree(params).findPath("searchword");
		String searchword = jsonSearchWord.asText();
		JsonNode jsonLimit = objectMapper.readTree(params).findPath("limit");
		int limit = jsonLimit.asInt();
	
		boardParamDto.setOffset(offset);
		boardParamDto.setSearchWord(searchword);
		boardParamDto.setLimit(limit);

		if( boardParamDto.getSearchWord() == "" ) {
			boardResultDto = service.boardList(boardParamDto);
		}
		else {
			boardResultDto = service.boardListSearchWord(boardParamDto);
		}
		
		if( boardResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/boards/count")
	private ResponseEntity<Integer> boardListCount(BoardParamDto boardParamDto){
		
		if( boardParamDto.getSearchWord().isEmpty() ) {
			return new ResponseEntity<Integer>( service.boardListTotalCount(), HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>( service.boardListSearchWordTotalCount(boardParamDto), HttpStatus.OK);
		}
		 
	}
	
	@GetMapping(value="/boards/{boardId}")
	private ResponseEntity<BoardResultDto> boardDetail(@PathVariable int boardId, HttpSession session){

		BoardParamDto boardParamDto = new BoardParamDto();
		boardParamDto.setBoardId(boardId);
		BoardResultDto boardResultDto = service.boardDetail(boardParamDto);

		if( boardResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PostMapping(value="/boards")
	private ResponseEntity<BoardResultDto> boardInsert(
			@RequestBody String params, 
			@RequestParam(value="file", required = false) MultipartFile file) throws Exception {
		
		BoardDto boardDto=new BoardDto();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonTitle = objectMapper.readTree(params).findPath("title");
		String title = jsonTitle.asText();
		JsonNode jsonContent = objectMapper.readTree(params).findPath("content");
		String content = jsonContent.asText();
		JsonNode jsonUserSeq = objectMapper.readTree(params).findPath("userSeq");
		int userSeq = jsonUserSeq.asInt();
		
		boardDto.setTitle(title);
		boardDto.setContent(content);
		boardDto.setUserSeq(userSeq);
		
		
		BoardResultDto boardResultDto = service.boardInsert(boardDto, file);
		
		if( boardResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PostMapping(value="/boards/{boardId}") 
	private ResponseEntity<BoardResultDto> boardUpdate(
			@RequestBody String params, 
			@RequestParam(value="file", required = false) MultipartFile file)throws Exception{
		
		BoardDto boardDto=new BoardDto();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonTitle = objectMapper.readTree(params).findPath("title");
		String title = jsonTitle.asText();
		JsonNode jsonContent = objectMapper.readTree(params).findPath("content");
		String content = jsonContent.asText();
		JsonNode jsonBoardId = objectMapper.readTree(params).findPath("boardId");
		int boardId = jsonBoardId.asInt();
		
		boardDto.setBoardId(boardId);
		boardDto.setContent(content);
		boardDto.setTitle(title);
	
		
		BoardResultDto boardResultDto = service.boardUpdate(boardDto, file);
		
		if( boardResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@DeleteMapping(value="/boards/{boardId}") 
	private ResponseEntity<BoardResultDto> boardDelete(@PathVariable(value="boardId") int boardId){
		BoardResultDto boardResultDto = service.boardDelete(boardId);
		
		if( boardResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}

}
