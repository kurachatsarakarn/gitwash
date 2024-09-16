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
import org.springframework.web.multipart.MultipartFile;

import ac.th.ku.soa.washsystem.service.OrdersSrevice;
import ac.th.ku.soa.washsystem.service.dto.Ordersdto;
import lombok.Delegate;

@RestController
public class OrdersController {
	
	@Autowired
	private OrdersSrevice orders;
	
	@PostMapping("/orders/cerate")
	public ResponseEntity<Map<String,Object>> cerate(
			@RequestBody Ordersdto.OrdersCerate ordersReq
			){
		Map<String,Object> map =  orders.cerate(ordersReq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@GetMapping("/emp/orders/readAll")
	public ResponseEntity<Map<String,Object>> readAll(){
		Map<String,Object> map =  orders.readAll();
		return ResponseEntity.status((int)map.get("code")).body(map);
	}
	
	@GetMapping("/orders/readById")
	public ResponseEntity<Map<String,Object>> read(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = orders.read(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@PutMapping("/emp/orders/Status")
	public ResponseEntity<Map<String,Object>> status(
			@RequestBody Ordersdto.OrdersUpdateStatus ordersReq
			){
		Map<String,Object> map = orders.updateStatus(ordersReq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@PutMapping("/emp/orders/EmpByCheck")
	public ResponseEntity<Map<String,Object>> EmpByCheck(
			@RequestBody Ordersdto.OrdersUpdateEmp ordersReq
			){
		Map<String,Object> map = orders.updateEmpByCheck(ordersReq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@PutMapping("/emp/orders/EmpBySender")
	public ResponseEntity<Map<String,Object>> EmpBySender(
			@RequestBody Ordersdto.OrdersUpdateEmp ordersReq
			){
		Map<String,Object> map = orders.updateEmpBySender(ordersReq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@PutMapping("/emp/orders/EmpByReciever")
	public ResponseEntity<Map<String,Object>> EmpByReciever(
			@RequestBody Ordersdto.OrdersUpdateEmp ordersReq
			){
		Map<String,Object> map = orders.updateEmpByReciever(ordersReq);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	@DeleteMapping("/orders/delete")
	public ResponseEntity<Map<String,Object>> delete(
			@RequestParam("id") Integer id
			){
		Map<String,Object> map = orders.delete(id);
		return ResponseEntity.status((int)map.get("code")).body(map);

	}
	
	
	@PostMapping(value = "/orders/upload", consumes = "multipart/form-data")
	public ResponseEntity<Map<String, Object>> upload(
	        @RequestParam("id") Integer id,
	        @RequestParam("file") MultipartFile file) {
	    Map<String, Object> map = orders.upload(file, id);
	    return ResponseEntity.status((int) map.get("code")).body(map);
	}
	
}
