package com.kh.lucky.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.ReviewDao;
import com.kh.lucky.dto.ReviewDto;
import com.kh.lucky.vo.ReviewDataVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "이용후기", description = "Review 테이블 CRUD 처리")
@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewRestController {
	@Autowired
	private ReviewDao reviewDao;
	
	//등록
	@Operation(
		description = "이용후기 등록",
		responses = {
			@ApiResponse(responseCode = "200",description = "이용후기 등록 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ReviewDto.class)
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
	public ReviewDto save(@RequestBody ReviewDto reviewDto) {
		//데이터 확인
	    System.out.println("서버에서 나오는지?");
	    System.out.println("Review No: " + reviewDto.getReviewNo());
	    System.out.println("Review Title: " + reviewDto.getReviewTitle());
	    System.out.println("Review Content: " + reviewDto.getReviewContent());
	    System.out.println("Review Star: " + reviewDto.getReviewStar());
	    System.out.println("Review Writer: " + reviewDto.getReviewWriter());
	    System.out.println("Review ViewCount: " + reviewDto.getReviewViewCount());
	    System.out.println("Review Wtime: " + reviewDto.getReviewWtime());
		
	    // reviewWtime 설정
	    //reviewDto.setReviewWtime(new Date());

	    // reviewWriter 설정 (예시: 사용자의 ID)
	    reviewDto.setReviewWriter("user1"); // 사용자의 ID로 설정 (실제 사용자 ID를 설정해야 함)
	    
	    
	    //시퀀스 번호 생성
		int sequence = reviewDao.sequence();
		//번호 설정
		reviewDto.setReviewNo(sequence);
		//등록
		reviewDao.insert(reviewDto);
		//결과 조회 반환
		return reviewDao.selectOne(sequence);
	}
	
	
	
	//리뷰 목록 조회
	@Operation(
		description = "이용후기 목록",
		responses = {
			@ApiResponse(responseCode = "200",description = "이용후기 목록 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ReviewDto.class)
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
	public ResponseEntity<List<ReviewDto>> list(){
		List<ReviewDto> list = reviewDao.selectList();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/page/{page}/size/{size}")
	public ReviewDataVO list(@PathVariable int page, @PathVariable int size) {
		List<ReviewDto> list = reviewDao.selectListByPaging(page, size);
		int count = reviewDao.count();
		int endRow = page * size;
		boolean last = endRow >= count;
		return ReviewDataVO.builder()
					.list(list)//화면에 표시할 목록
					.count(count)//총 데이터 개수
					.last(last)//마지막 여부
				.build();
	}
}







