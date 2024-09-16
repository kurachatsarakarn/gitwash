package ac.th.ku.soa.washsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ac.th.ku.soa.washsystem.service.EmployeeService;
import ac.th.ku.soa.washsystem.service.dto.Employeedto;
import ac.th.ku.soa.washsystem.service.dto.logindto;



@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService emp;
	
	
	@PostMapping("/public/loginEmp")
	public ResponseEntity<Map<String, Object>>  loginEmp(
			@RequestBody logindto.logininfo logonreq
	){
		Map<String,Object> map =  emp.loginEmp(logonreq);	
		return ResponseEntity.status((int)map.get("code")).body(map);
		
	}
	
	@GetMapping("/emp/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map = emp.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@GetMapping("/emp/readById")
	public ResponseEntity<Map<String, Object>>  read(
			@RequestParam("id") Integer id
	){
		Map<String,Object> map =  emp.read(id);	
		return ResponseEntity.status((int)map.get("code")).body(map);
		
	}
	@PostMapping("/emp/cerate")
	public ResponseEntity<Map<String,Object>> create(
				@RequestBody Employeedto.EmployeeCreate Empreq
			){
		Map<String,Object> map = emp.cerate(Empreq);
		return ResponseEntity.status((int)map.get("code")).body(map);
	}
	
	
	@PostMapping("/emp/update")
	public ResponseEntity<Map<String,Object>> update(
				@RequestBody Employeedto.Employeeupdate Empreq
			){
		Map<String,Object> map = emp.update(Empreq);
		return ResponseEntity.status((int)map.get("code")).body(map);
	}
	
	@DeleteMapping("/emp/delete")
	public ResponseEntity<Map<String,Object>> delete(
				@RequestParam("id") Integer id
			){
		Map<String,Object> map = emp.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);
	}
}
