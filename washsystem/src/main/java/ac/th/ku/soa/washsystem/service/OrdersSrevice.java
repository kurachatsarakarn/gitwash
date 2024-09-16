package ac.th.ku.soa.washsystem.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ac.th.ku.soa.washsystem.entity.Employee;
import ac.th.ku.soa.washsystem.entity.Fabricsoftener;
import ac.th.ku.soa.washsystem.entity.MyPackage;
import ac.th.ku.soa.washsystem.entity.Orders;
import ac.th.ku.soa.washsystem.entity.User;
import ac.th.ku.soa.washsystem.entity.Watertmp;
import ac.th.ku.soa.washsystem.repository.EmployeeRepository;
import ac.th.ku.soa.washsystem.repository.FabricsoftenerRepository;
import ac.th.ku.soa.washsystem.repository.MypackageRespository;
import ac.th.ku.soa.washsystem.repository.OrdersRespository;
import ac.th.ku.soa.washsystem.repository.UserRepository;
import ac.th.ku.soa.washsystem.repository.WatertmpRepository;
import ac.th.ku.soa.washsystem.service.dto.Ordersdto;

@Service
public class OrdersSrevice {

	@Autowired
	private OrdersRespository OrdersRep;
	@Autowired
	private UserRepository UserRep;
	@Autowired
	private EmployeeRepository EmpRep;
	@Autowired
	private MypackageRespository MyRep;
	@Autowired
	private FabricsoftenerRepository FabRep;
	@Autowired
	private WatertmpRepository WaterRep;
	
	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
	
	public Map<String,Object> readAll(){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<Orders> order =  OrdersRep.findAll();
			if(order == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ORDERS");
				return map;
			}
			List<Ordersdto.Ordersinfo> orderdto = order.stream().map(
					ordermap -> new Ordersdto.Ordersinfo()
									.setId(ordermap.getId())
									.setStatus(ordermap.getStatus())
									.setIduser(ordermap.getUser().getId())
									.setUser(ordermap.getUser().getName())
									.setIdmypackage(ordermap.getMypackage().getId())
									.setMypackage(ordermap.getMypackage().getName())
									.setIdwatertmp(ordermap.getWatertmp().getId())
									.setWatertmp(ordermap.getWatertmp().getName())
									.setIdfabrisoftener(ordermap.getFabrisoftener().getId())
									.setFabrisoftener(ordermap.getFabrisoftener().getName())
									.setIdempbycheck(ordermap.getEmpbycheck() != null ? ordermap.getEmpbycheck().getId() : null)
									.setEmpbycheck(ordermap.getEmpbycheck() != null ? ordermap.getEmpbycheck().getName() : null)
									.setIdempbyreciever(ordermap.getEmpbyreciever() != null ? ordermap.getEmpbyreciever().getId() : null)
									.setEmpbyreciever(ordermap.getEmpbyreciever() != null ? ordermap.getEmpbyreciever().getName() : null)
									.setIdempbysender(ordermap.getEmpbysender() != null ? ordermap.getEmpbysender().getId() : null)
									.setEmpbysender(ordermap.getEmpbysender() != null ? ordermap.getEmpbysender().getName() : null))
						.collect(Collectors.toList());
			map.put("code", HttpStatus.OK.value());
			map.put("data", orderdto);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg",e.getMessage());
			return map;
		}
	}
	
	public Map<String,Object> read(Integer id){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Orders order =  OrdersRep.findById(id).orElse(null);
			if(order == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ORDERS");
				return map;
			}
			Ordersdto.Ordersinfo orderdto =  new Ordersdto.Ordersinfo()
									.setId(order.getId())
									.setStatus(order.getStatus())
									.setIduser(order.getUser().getId())
									.setUser(order.getUser().getName())
									.setIdmypackage(order.getMypackage().getId())
									.setMypackage(order.getMypackage().getName())
									.setIdwatertmp(order.getWatertmp().getId())
									.setWatertmp(order.getWatertmp().getName())
									.setIdfabrisoftener(order.getFabrisoftener().getId())
									.setFabrisoftener(order.getFabrisoftener().getName())
									.setIdempbycheck(order.getEmpbycheck() != null ? order.getEmpbycheck().getId() : null)
									.setEmpbycheck(order.getEmpbycheck() != null ? order.getEmpbycheck().getName() : null)
									.setIdempbyreciever(order.getEmpbyreciever() != null ? order.getEmpbyreciever().getId() : null)
									.setEmpbyreciever(order.getEmpbyreciever() != null ? order.getEmpbyreciever().getName() : null)
									.setIdempbysender(order.getEmpbysender() != null ? order.getEmpbysender().getId() : null)
									.setEmpbysender(order.getEmpbysender() != null ? order.getEmpbysender().getName() : null);
			map.put("code", HttpStatus.OK.value());
			map.put("data", orderdto);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg",e.getMessage());
			return map;
		}
	}
	
	@Transactional
	public Map<String, Object> cerate(Ordersdto.OrdersCerate Ordersreq){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(Ordersreq);
			if(error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg",String.join(",", error));
				return map;
			}
			User user = UserRep.findById(Ordersreq.getUser()).orElse(null);
			MyPackage my = MyRep.findById(Ordersreq.getMypackage()).orElse(null);
			Fabricsoftener fab = FabRep.findById(Ordersreq.getFabrisoftener()).orElse(null);
			Watertmp water = WaterRep.findById(Ordersreq.getWatertmp()).orElse(null);
			if(user == null || my == null || fab == null|| water == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg","NOT_FOUND_DATA");
				return map;
			}
			OrdersRep.save(new Orders().setFabrisoftener(fab).setMypackage(my).setStatus(Ordersreq.getStatus()).setUser(user).setWatertmp(water));
			map.put("code",HttpStatus.OK.value());
			map.put("msg", "cerate success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code",HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	@Transactional
	public Map<String, Object> updateStatus(Ordersdto.OrdersUpdateStatus ordersReq){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Orders orders =  OrdersRep.findById(ordersReq.getId()).orElse(null);
			if(orders == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
				OrdersRep.updateStatus(ordersReq.getId(), ordersReq.getStatus());
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	
	public Map<String, Object> updateEmpByCheck(Ordersdto.OrdersUpdateEmp ordersReq){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.print(ordersReq.getId()+"_dksodada\n");
			Orders orders =  OrdersRep.findById(ordersReq.getId()).orElse(null);
			Employee Emp = EmpRep.findById(ordersReq.getEmp()).orElse(null);
			System.out.print(orders.getId()+"_222222");
			if(orders == null || Emp == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID_OR_EMPLOYEE");
				return map;
			}
				OrdersRep.updateEmpByCheck(orders.getId(), Emp);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	public Map<String, Object> updateEmpBySender(Ordersdto.OrdersUpdateEmp ordersReq){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Orders orders =  OrdersRep.findById(ordersReq.getId()).orElse(null);
			Employee employee = EmpRep.findById(ordersReq.getEmp()).orElse(null);
			if(orders == null || employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID_OR_EMPLOYEE");
				return map;
			}
				OrdersRep.updateEmpBySender(ordersReq.getId(), employee);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;

		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	public Map<String, Object> updateEmpByReciever(Ordersdto.OrdersUpdateEmp ordersReq){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Orders orders =  OrdersRep.findById(ordersReq.getId()).orElse(null);
			Employee employee = EmpRep.findById(ordersReq.getEmp()).orElse(null);
			if(orders == null || employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID_OR_EMPLOYEE");
				return map;
			}
				OrdersRep.updateEmpByReciever(ordersReq.getId(), employee);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	
	
	public Map<String, Object> delete(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Orders orders =  OrdersRep.findById(id).orElse(null);
			if(orders == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			if(orders.getStatus().equals("Payment Pending")) {
				OrdersRep.delete(orders);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;
				
			}
			map.put("code", HttpStatus.CONFLICT.value());
			map.put("msg", "STATUS_ISNOT_PAYMENT_PENDING");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	public Map<String,Object> upload(MultipartFile file,Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			 if (file.isEmpty()) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", "Please select a file to upload");
					return map;
		        }
	            File uploadDir = new File(UPLOAD_DIR);
	            if (!uploadDir.exists()) {
	                uploadDir.mkdirs();
	            }

	            // ตั้งชื่อไฟล์ (อาจปรับเปลี่ยนตามที่ต้องการ)
	            String fileName = "ID_ORDER_"+id+"_"+System.currentTimeMillis() + "_" + file.getOriginalFilename();

	            // บันทึกไฟล์
	            Path filePath = Paths.get(UPLOAD_DIR + fileName);
	            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "File uploaded successfully:"+fileName);
				return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	private List<String> validate(Ordersdto.OrdersCerate Ordersreq) {
		List<String> error = new ArrayList<String>();
		if(Ordersreq.getUser() == null) {
			error.add("NOT_FOUND_USER");
		}
		if(Ordersreq.getMypackage() == null) {
			error.add("NOT_FOUND_Mypackage");
		}
		if(Ordersreq.getFabrisoftener() == null) {
			error.add("NOT_FOUND_Fabrisoftener");
		}
		if(Ordersreq.getWatertmp() == null) {
			error.add("NOT_FOUND_Watertmp");
		}
		if(Ordersreq.getStatus() == null) {
			error.add("NOT_FOUND_Status");
		}
		return error;
	}
}
