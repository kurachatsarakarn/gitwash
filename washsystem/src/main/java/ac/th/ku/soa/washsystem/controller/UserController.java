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

import ac.th.ku.soa.washsystem.entity.User;
import ac.th.ku.soa.washsystem.service.UserService;
import ac.th.ku.soa.washsystem.service.dto.Userdto;
import ac.th.ku.soa.washsystem.service.dto.logindto;

@RestController
public class UserController {
	@Autowired
	public UserService user;
	
	@PostMapping("/public/loginUser")
	public ResponseEntity<Map<String, Object>>  loginEmp(
			@RequestBody logindto.logininfo logonreq
	){
		Map<String,Object> map =  user.loginUser(logonreq);	
		return ResponseEntity.status((int)map.get("code")).body(map);
		
	}
	
	
	
	@GetMapping("/user/readById")
	public ResponseEntity<Map<String,Object>> read(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = user.read(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@GetMapping("/user/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map = user.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PostMapping("/user/cerate")
	public ResponseEntity<Map<String,Object>> cerate(
			@RequestBody Userdto.UserCerate userreq
			){
		Map<String,Object> map = user.create(userreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PutMapping("/user/update")
	public ResponseEntity<Map<String,Object>> update(
			@RequestBody User userreq
			){
		Map<String,Object> map = user.update(userreq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@DeleteMapping("/user/delete")
	public ResponseEntity<Map<String,Object>> delete(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = user.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);
	
}
}
