package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.NoticeDao;
import com.kh.lucky.dto.NoticeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "공지사항", description = "Notice 테이블 CRUD 처리")
@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeRestController {
	@Autowired
	private NoticeDao noticeDao;
	
	//등록
	@Operation(
		description = "공지사항 등록",
		responses = {
			@ApiResponse(responseCode = "200",description = "공지사항 등록 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = NoticeDto.class)
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@PostMapping("/")
	public NoticeDto save(@RequestBody NoticeDto noticeDto) {
		//시퀀스 번호 생성
		int sequence = noticeDao.sequence();
		//번호 설정
		noticeDto.setNoticeNo(sequence);
		//등록
		noticeDao.insert(noticeDto);
		//결과 조회 반환
		return noticeDao.selectOne(sequence);
	}
	
	//목록
	@Operation(
		description = "공지사항 목록 조회",
		responses = {
			@ApiResponse(responseCode = "200",description = "조회 완료",
				content = @Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = NoticeDto.class))
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@GetMapping("/")
	public ResponseEntity<List<NoticeDto>> list(){
		List<NoticeDto> list = noticeDao.selectList();
		return ResponseEntity.ok().body(list);
	}
	
	//상세 목록
	@Operation(
		description = "공지사항 상세 조회",
		responses = {
			@ApiResponse(responseCode = "200",description = "조회 완료",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = NoticeDto.class)
					)
			),
			@ApiResponse(responseCode = "404",description = "공지사항 없음",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@GetMapping("/{noticeNo}")
	public ResponseEntity<NoticeDto> find(@PathVariable int noticeNo){
		NoticeDto noticeDto = noticeDao.selectOne(noticeNo);
		if(noticeDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(noticeDto);
	}
	
	//수정
	@Operation(
		description = "공지사항 수정",
		responses = {
			@ApiResponse(responseCode = "200",description = "변경 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = NoticeDto.class)
				)
			),
			@ApiResponse(responseCode = "404",description = "공지사항 없음",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@PatchMapping("/")
	public ResponseEntity<NoticeDto> edit(@RequestBody NoticeDto noticeDto) {
		boolean result = noticeDao.edit(noticeDto);
		//업데이트 실패한 경우 404에러 반환
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		//수정 완료된 결과를 조회하여 반환
		return ResponseEntity.ok().body(noticeDao.selectOne(noticeDto.getNoticeNo()));
	}
	
	//삭제
	@Operation(
		description = "공지사항 삭제",
		responses = {
			@ApiResponse(responseCode = "200",description = "삭제 완료",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("ok")
				)
			),
			@ApiResponse(responseCode = "404",description = "공지사항 없음",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@DeleteMapping("/{noticeNo}")
	public ResponseEntity<String> delete(@PathVariable int noticeNo) {
		boolean result = noticeDao.delete(noticeNo);
		if(result == false) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}

}
