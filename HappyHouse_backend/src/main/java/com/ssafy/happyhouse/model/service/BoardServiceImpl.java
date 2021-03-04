package com.ssafy.happyhouse.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.BoardFileDto;
import com.ssafy.happyhouse.model.BoardParamDto;
import com.ssafy.happyhouse.model.BoardResultDto;
import com.ssafy.happyhouse.model.dao.BoardDao;

	@Service

	public class BoardServiceImpl implements BoardService {
	
		@Autowired
		BoardDao dao;
		String uploadFolder = "upload";
	
	/* for production code */
//	String uploadPath = getServletContext().getRealPath("/") + File.separator + uploadFolder;
//	System.out.println("BoardServlet : boardInsert : uploadPath " + uploadPath);
//	//C:\EDU_HOME\java\eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\BoardWebFileUpload\\upload
	
	/* for eclipse development code */
	//D:\SSAFY\java\eclipse\ssafy4_sts3\BoardFileUploadSpringMVCMybatis
		//D:\SSAFY\workspaceBoot\HappyHouse6
	String uploadPath = "D:" + File.separator + "SSAFY" + File.separator + "workspaceBoot" + File.separator + 
			"HappyHouse6" +  File.separator + "src" + File.separator + "main" + 
			File.separator + "webapp" + File.separator + "resources" + File.separator + "static";
	
	//C:\SSAFY\boot\BoardFileUploadSpringBootMybatis\src\main\resources
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;

	@Override
	public BoardResultDto boardInsert(BoardDto dto, MultipartFile uploadFile) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			dao.boardInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"
			
			if( uploadFile != null && ! uploadFile.isEmpty() ) {
				int boardId = dto.getBoardId();

				File uploadDir = new File(uploadPath + File.separator + uploadFolder);
				if (!uploadDir.exists()) uploadDir.mkdir();

				String fileName = uploadFile.getOriginalFilename();
		    
			    //Random File Id
				UUID uuid = UUID.randomUUID();
			
				//file extension
				//String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
				int pos=fileName.lastIndexOf(".");
				String extension=fileName.substring(pos+1);
				
				String savingFileName = uuid + "." + extension;
			
				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				uploadFile.transferTo(destFile);
		    
			    // Table Insert
			    BoardFileDto boardFileDto = new BoardFileDto();
			    boardFileDto.setBoardId(boardId);
			    boardFileDto.setFileName(fileName);
			    boardFileDto.setFileSize(uploadFile.getSize());
				boardFileDto.setFileContentType(uploadFile.getContentType());
				String boardFileUrl = uploadFolder + "/" + savingFileName;
				boardFileDto.setFileUrl(boardFileUrl);
				
				dao.boardFileInsert(boardFileDto);
			}
			
			boardResultDto.setResult(SUCCESS);
			
		}catch(IOException e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardUpdate(BoardDto dto, MultipartFile uploadFile){
		
		BoardResultDto boardResultDto = new BoardResultDto();

		try {
			dao.boardUpdate(dto);
			
			if( uploadFile != null && ! uploadFile.isEmpty() ) {
										
				File uploadDir = new File(uploadPath + File.separator + uploadFolder);
				if (!uploadDir.exists()) uploadDir.mkdir();
				
				// 臾쇰━ �뙆�씪 �궘�젣, 泥⑤��뙆�씪 �뿬�윭媛� 怨좊젮
		    	List<String> fileUrlList = dao.boardFileUrlDeleteList(dto.getBoardId());	
				for(String fileUrl : fileUrlList) {	
					File file = new File(uploadPath + File.separator, fileUrl);
					if(file.exists()) {
						file.delete();
					}
				}

		    	dao.boardFileDelete(dto.getBoardId()); // �뀒�씠釉� �뙆�씪 �궘�젣
				
		    	String fileName = uploadFile.getOriginalFilename();
	    
			    //Random File Id
				UUID uuid = UUID.randomUUID();
			
				//file extension
				//String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
			
				int pos=fileName.lastIndexOf(".");
				String extension=fileName.substring(pos+1);
				
				String savingFileName = uuid + "." + extension;

				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				uploadFile.transferTo(destFile);
		    
			    // Table Insert
			    BoardFileDto boardFileDto = new BoardFileDto();
			    boardFileDto.setBoardId(dto.getBoardId());
			    boardFileDto.setFileName(fileName);
			    boardFileDto.setFileSize(uploadFile.getSize());
				boardFileDto.setFileContentType(uploadFile.getContentType());
				String boardFileUrl = uploadFolder + "/" + savingFileName;
				boardFileDto.setFileUrl(boardFileUrl);
				
				dao.boardFileInsert(boardFileDto);
			}
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardDelete(int boardId) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			dao.boardDelete(boardId);		
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardDetail(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {

			BoardDto dto = dao.boardDetail(boardParamDto);

			boardResultDto.setDto(dto);
			
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardList(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<BoardDto> list = dao.boardList(boardParamDto);
			int count = dao.boardListTotalCount();			
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public int boardListTotalCount() {
		return dao.boardListTotalCount();
	}

	@Override
	public BoardResultDto boardListSearchWord(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<BoardDto> list = dao.boardListSearchWord(boardParamDto);
			int count = dao.boardListSearchWordTotalCount(boardParamDto);
			
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			
			boardResultDto.setResult(SUCCESS);
		
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public int boardListSearchWordTotalCount(BoardParamDto paramDto) {
		return dao.boardListSearchWordTotalCount(paramDto);
	}
}