package ac.th.ku.soa.washsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.th.ku.soa.washsystem.entity.Employee;
import ac.th.ku.soa.washsystem.entity.User;
import ac.th.ku.soa.washsystem.repository.EmployeeRepository;
import ac.th.ku.soa.washsystem.security.JwtUtil;
import ac.th.ku.soa.washsystem.service.dto.Employeedto;
import ac.th.ku.soa.washsystem.service.dto.Userdto;
import ac.th.ku.soa.washsystem.service.dto.logindto;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository EmpRep;
	@Autowired
	private JwtUtil jwtUtil;
	
	public Map<String, Object> read(Integer id) {
		Map<String, Object> map = new HashMap<>();
		try {
			Employee employee = EmpRep.findById(id).orElse(null);

			if (employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_EMPLOYEE");
				return map;
			} else {
				map.put("code", HttpStatus.OK.value());
				map.put("data", new Employeedto.EmployeeInfo().setId(employee.getId()).setName(employee.getName())
						.setPosition(employee.getPosition()));

				return map;
			}
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}

	}
	
	public Map<String, Object> readAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Employee> Emp = EmpRep.findAll();
			if (Emp.size() == 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_DATA");
				return map;
			}
			List<Employeedto.EmployeeInfo> userdto = Emp.stream()
					.map(Empmap -> new Employeedto.EmployeeInfo()
							.setId(Empmap.getId())
							.setName(Empmap.getName())
							.setPosition(Empmap.getPosition()))
					.collect(Collectors.toList());
			map.put("code", HttpStatus.OK.value());
			map.put("data", userdto);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}

	}
	
	
	public Map<String,Object> loginEmp(logindto.logininfo loginreq) {
		Map<String, Object> map = new HashMap<>();
		try {
			Employee employee = EmpRep.findByUserNameAndPass(loginreq.getUsername(),loginreq.getPassword());
			if(employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_USERNAME_OR_PASS");
				return map;
			}
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "login success");
			map.put("token", jwtUtil.generateToken(employee.getUserName()));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	

	@Transactional
	public Map<String, Object> cerate(Employeedto.EmployeeCreate Empreq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<String> error = validate(new Employee().setName(Empreq.getName()).setPosition(Empreq.getPosition())
					.setPass(Empreq.getPassword()).setUserName(Empreq.getUsername()));
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			EmpRep.save(new Employee().setName(Empreq.getName()).setPosition(Empreq.getPosition())
					.setUserName(Empreq.getUsername()).setPass(Empreq.getPassword()));
			map.put("code", HttpStatus.OK.value());
			map.put("data", Empreq);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
	}
	
	@Transactional
	public Map<String, Object> update(Employeedto.Employeeupdate Empreq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Employee employee = EmpRep.findById(Empreq.getId()).orElse(null);
			List<String> error = validate(new Employee().setName(Empreq.getName()).setPosition(Empreq.getPosition())
					.setPass(Empreq.getPassword()).setUserName(Empreq.getUsername()));
			if (employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			
			EmpRep.save(new Employee().setId(Empreq.getId()).setName(Empreq.getName()).setPosition(Empreq.getPosition()).setUserName(Empreq.getUsername()).setPass(Empreq.getPassword()));
			map.put("code", HttpStatus.OK.value());
			map.put("data", Empreq);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}

	}
	
	public Map<String,Object> delete(Integer id){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Employee employee = EmpRep.findById(id).orElse(null);
			if(employee == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			EmpRep.delete(employee);
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "delete success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
	}

	
	private List<String> validate(Employee Empreq) {
		List<String> error = new ArrayList<String>();
		System.out.print(Empreq.getName() + Empreq.getPosition() + Empreq.getUserName() + Empreq.getPass());
		if (Empreq.getName() == null) {
			error.add("Name_NOT_FOUND");
		}
		if (Empreq.getPosition() == null) {
			error.add("Position_NOT_FOUND");
		}
		if (Empreq.getUserName() == null) {
			error.add("Username_NOT_FOUND");
		}
		if (Empreq.getPass() == null) {
			error.add("Pass_NOT_FOUND");
		}

		return error;
	}
}
