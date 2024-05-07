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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.BusDao;
import com.kh.lucky.dto.BusDto;
import com.kh.lucky.vo.BusVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "버스 관리", description = "bus CRUD 구현")

@CrossOrigin
@RestController
@RequestMapping("/bus")
public class BusRestController {

	@Autowired
	private BusDao busDao;

	// 등록
	@PostMapping("/")
	public BusDto insert(@RequestBody BusDto busDto) {
		int sequence = busDao.sequence();
		busDto.setBusNo(sequence);
		busDao.insert(busDto);
		return busDao.selectOne(sequence);
	}

	// 조회 전체
	@GetMapping("/")
	public List<BusDto> selectList() {
		return busDao.selectList();
	}

	// 조회 단일
	@GetMapping("/{busNo}")
	public ResponseEntity<BusDto> find(@PathVariable int busNo) {
		BusDto busDto = busDao.selectOne(busNo);
		if (busDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(busDto); // 정보가있으면 바디로 조회
	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody BusDto busDto) {
		boolean result = busDao.editAll(busDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody BusDto busDto) {
		boolean result = busDao.edit(busDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 삭제
	@DeleteMapping("/{busNo}")
	public ResponseEntity<?> delete(@PathVariable int busNo) {
		BusDto busDto = busDao.selectOne(busNo);
		boolean result = busDao.delete(busNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(busDto);
	}

	// 버스 번호 조회해서 차량번호 가져오기
	@GetMapping("/busNum/{busNo}")
	public ResponseEntity<BusVO> getBusNum(@PathVariable int busNo) {
		BusVO busVO = busDao.getBusNum(busNo);
		if (busVO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(busVO);
	}
}
