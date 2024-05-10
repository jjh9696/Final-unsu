package com.kh.lucky.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.RouteDao;
import com.kh.lucky.dto.RouteDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "노선 관리", description = "route CRUD 구현")

@CrossOrigin
@RestController
@RequestMapping("/route")
public class RouteRestController {

	@Autowired
	private RouteDao routeDao;

	// 등록
	@PostMapping("/")
	public RouteDto insert(@RequestBody RouteDto routeDto) {
		int sequence = routeDao.sequence(); // 시퀀스 생성하고
		routeDto.setRouteNo(sequence); // 세팅해주고
		routeDao.insert(routeDto); // 저장
		return routeDao.selectOne(sequence); // 시퀀스 번호로 반환
	}

	// 전체 조회
	@GetMapping("/")
	public List<RouteDto> selectList() {
		return routeDao.selectList();
	}

	// 조회 단일
	@GetMapping("/{routeNo}")
	public ResponseEntity<RouteDto> find(@PathVariable int routeNo) {
		RouteDto routeDto = routeDao.selectOne(routeNo);
		if (routeDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(routeDto);
	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody RouteDto routeDto) {
		boolean result = routeDao.editAll(routeDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody RouteDto routeDto) {
		boolean result = routeDao.edit(routeDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 삭제
	@DeleteMapping("/{routeNo}")
	public ResponseEntity<?> delete(@PathVariable int routeNo) {
		RouteDto routeDto = routeDao.selectOne(routeNo);
		boolean result = routeDao.delete(routeNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(routeDto);
	}
	
	//시간조회
//	@GetMapping("/time")
//	public List<RouteTimesVO> getTimeList() {
//		return routeDao.getTimeList();
//	}
//	@GetMapping("/time/{routeStart}/{routeEnd}")
//	public ResponseEntity<List<RouteTimesVO>> getTimeList(@PathVariable int routeStart, @PathVariable int routeEnd){
//		List<RouteTimesVO> routeTimes = routeDao.getTimeList(routeStart, routeEnd);
//		 if(routeTimes == null) {
//		        return ResponseEntity.notFound().build();
//		    }
//		    return ResponseEntity.ok().body(routeTimes);
//		}
}
