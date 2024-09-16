package ac.th.ku.soa.washsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ac.th.ku.soa.washsystem.entity.Watertmp;
import ac.th.ku.soa.washsystem.service.WatertmpService;
import ac.th.ku.soa.washsystem.service.dto.Watertmpdto;

@RestController
public class WatertmpController {
	@Autowired
	public WatertmpService water;
	
	@GetMapping("/water/readById")
	public ResponseEntity<Map<String,Object>> read(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = water.read(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@GetMapping("/water/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map = water.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PostMapping("/emp/water/cerate")
	public ResponseEntity<Map<String,Object>> cerate(
			@RequestBody Watertmpdto.Watertmpinfo waterreq
			){
		Map<String,Object> map = water.create(waterreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PutMapping("/emp/water/update")
	public ResponseEntity<Map<String,Object>> update(
			@RequestBody Watertmp waterreq
			){
		Map<String,Object> map = water.update(waterreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@DeleteMapping("/emp/water/delete")
	public ResponseEntity<Map<String,Object>> delete(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = water.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);
	
}
}
