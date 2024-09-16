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

import ac.th.ku.soa.washsystem.service.FabricsoftenerService;
import ac.th.ku.soa.washsystem.service.dto.Fabricsoftenerdto;

@RestController
public class FabricsoftenerController {

	@Autowired
	public FabricsoftenerService Fab;
	
	@GetMapping("/fab/readById")
	public ResponseEntity<Map<String,Object>> read(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = Fab.read(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@GetMapping("/fab/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map = Fab.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PostMapping("/emp/fab/cerate")
	public ResponseEntity<Map<String,Object>> cerate(
			@RequestBody Fabricsoftenerdto.FabricsoftenerCarate Fabreq
			){
		Map<String,Object> map = Fab.create(Fabreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PutMapping("/emp/fab/update")
	public ResponseEntity<Map<String,Object>> update(
			@RequestBody Fabricsoftenerdto.Fabricsoftenerinfo Fabreq
			){
		Map<String,Object> map = Fab.update(Fabreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@DeleteMapping("/emp/fab/delete")
	public ResponseEntity<Map<String,Object>> delete(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = Fab.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);
	
}
}
