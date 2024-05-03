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
import com.kh.lucky.vo.NoticeDataVO;
import com.kh.lucky.vo.PageVO;

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
		//조회수 증가
		noticeDao.updateNoticeViewCount(noticeNo);
		
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
	
	//조회수 순서로 조회
	@Operation(
		description = "조회수로 공지사항 목록 조회",
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
	@GetMapping("/viewCount")
	public ResponseEntity<List<NoticeDto>> viewCountList(){
		List<NoticeDto> list = noticeDao.viewCountList();
		return ResponseEntity.ok().body(list);
	}
	//검색 조회
	@Operation(
	description = "키워드, 컬럼 공지사항 조회",
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
	@PostMapping("/search/column/{column}/keyword/{keyword}")
	public List<NoticeDto> searchList(@PathVariable String column,
									@PathVariable String keyword){
			//System.out.println("column : " + column + ", keyword : " + keyword);  
		return noticeDao.searchList(column, keyword);
	}
		
	//최신순 페이지네이션 + 무한스크롤
	@Operation(
	description = "최신순 페이지 공지사항 조회",
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
//	@GetMapping("/page/{page}/size/{size}")
//	public NoticeDataVO list(@PathVariable int page, @PathVariable int size) {
//		List<NoticeDto> list = noticeDao.selectListByPaging(page, size);
//		int count = noticeDao.count();
//		int endRow = page * size;
//		boolean last = endRow >= count;
//		return NoticeDataVO.builder()
//					.list(list)//화면에 표시할 목록
//					.count(count)//총 데이터 개수
//					.last(last)//마지막 여부(마지막 확인시 : true, 아닐시 : false)
//				.build();
//	}
	//페이지내비게이터 구현 
	@GetMapping("/page/{page}/size/{size}")
	public NoticeDataVO list(@PathVariable int page, @PathVariable int size) {
		
		int beginRow = page * size - (size-1);
		int endRow = page * size;
	    
	    List<NoticeDto> list = noticeDao.selectListByPaging(beginRow, endRow); // 페이지 번호와 페이지 크기를 이용하여 데이터 조회
	    int count = noticeDao.count(); // 전체 데이터 개수 조회
	    int totalPages = count / size + 1; // 전체 페이지 수 계산

	    PageVO pageVO = PageVO.builder()
	                        .page(page) // 현재 페이지 번호 설정
	                        .size(size) // 페이지 크기 설정
	                        .count(count) // 전체 개수 설정
	                        .build();
	    //System.out.println("list : " + list);
	    //System.out.println("count (목록개수): " + count);
	    //System.out.println("page (현재 페이지 번호): " + page);
	    //System.out.println("size (페이지 크기): " + size);
	    //System.out.println("totalPages (전체페이지수): " + totalPages);
	    //System.out.println("offset (페이지 번호 , 크기): " + offset);

	    return NoticeDataVO.builder()
	            .list(list) // 화면에 표시할 목록 설정
	            .pageVO(pageVO) // 페이지 정보 설정
	            .build();
	}
		
	
	//조회순 페이지네이션 + 무한스크롤
	@Operation(
	description = "조회순 페이지 공지사항 조회",
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
	@GetMapping("/viewPage/{page}/viewSize/{size}")

	public NoticeDataVO viewList(@PathVariable int page, @PathVariable int size) {
		
		int beginRow = page * size - (size-1);
		int endRow = page * size;
	    
		List<NoticeDto> list = noticeDao.selectViewListByPaging(beginRow, endRow); // 페이지 번호와 페이지 크기를 이용하여 데이터 조회
	    int count = noticeDao.count(); // 전체 데이터 개수 조회
	    int totalPages = count / size + 1; // 전체 페이지 수 계산

	    PageVO pageVO = PageVO.builder()
	                        .page(page) // 현재 페이지 번호 설정
	                        .size(size) // 페이지 크기 설정
	                        .count(count) // 전체 개수 설정
	                        .build();
	    return NoticeDataVO.builder()
	            .list(list) // 화면에 표시할 목록 설정
	            .pageVO(pageVO) // 페이지 정보 설정
	            .build();
	    
	}
	
	
}






