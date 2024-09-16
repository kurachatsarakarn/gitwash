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

import ac.th.ku.soa.washsystem.entity.MyPackage;
import ac.th.ku.soa.washsystem.service.MypackageService;
import ac.th.ku.soa.washsystem.service.dto.Mypackagedto;

@RestController
public class MypackageController {
	@Autowired
	public MypackageService mypackage;
	
	@GetMapping("/mypackage/readById")
	public ResponseEntity<Map<String,Object>> read(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = mypackage.read(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@GetMapping("/mypackage/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map = mypackage.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PostMapping("/emp/mypackage/cerate")
	public ResponseEntity<Map<String,Object>> cerate(
			@RequestBody Mypackagedto.MypackageCreate myreq
			){
		Map<String,Object> map = mypackage.create(myreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PutMapping("/emp/mypackage/update")
	public ResponseEntity<Map<String,Object>> update(
			@RequestBody MyPackage myreq
			){
		Map<String,Object> map = mypackage.update(myreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@DeleteMapping("/emp/mypackage/delete")
	public ResponseEntity<Map<String,Object>> delete(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = mypackage.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);
	
}
}
