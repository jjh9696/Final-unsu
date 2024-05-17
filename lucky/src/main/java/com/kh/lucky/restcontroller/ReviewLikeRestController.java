package com.kh.lucky.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.ReviewDao;
import com.kh.lucky.dao.ReviewLikeDao;
import com.kh.lucky.service.JwtService;
import com.kh.lucky.vo.LikeVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "리뷰 좋아요", description = "likeReview 테이블 CRUD 처리")
@CrossOrigin
@RestController
@RequestMapping("/ReviewLike")
public class ReviewLikeRestController {
	@Autowired
	private ReviewLikeDao reviewLikeDao;
	@Autowired
    private JwtService jwtService;
	@Autowired
	private ReviewDao reviewDao;
	
	
	@Operation(
		description = "이용후기 좋아요 토글",
		responses = {
			@ApiResponse(responseCode = "200",description = "이용후기 좋아요 등록 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = LikeVO.class)
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
    //좋아요 토글
    @PostMapping("/like/{reviewNo}")
    public ResponseEntity<LikeVO> toggleLike(@PathVariable int reviewNo,
                                             @RequestHeader("Authorization") String token) {
        //현재 로그인 아이디 추출
        String memberId = jwtService.parse(token).getMemberId();

        //좋아요 토글
        boolean liked = reviewLikeDao.toggle(memberId, reviewNo);

        //좋아요 개수 조회
        int likeCount = reviewLikeDao.count(reviewNo);

        //좋아요 상태와 개수를 LikeVO에 담아서 반환
        LikeVO likeVO = LikeVO.builder()
                                .state(liked)
                                .count(likeCount)
                                .build();

        return ResponseEntity.ok().body(likeVO);
    }}

