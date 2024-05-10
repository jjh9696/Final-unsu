package com.kh.lucky.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.ReviewDao;
import com.kh.lucky.dto.ReviewDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "이용후기", description = "Review 테이블 CRUD 처리")
@CrossOrigin
@RestController
@RequestMapping("review")
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
		//시퀀스 번호 생성
		int sequence = reviewDao.sequence();
		//번호 설정
		reviewDto.setReviewNo(sequence);
		//등록
		reviewDao.insert(reviewDto);
		//결과 조회 반환
		return reviewDao.selectOne(sequence);
	}
	
	
}
