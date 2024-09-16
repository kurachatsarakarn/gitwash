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
import ac.th.ku.soa.washsystem.repository.UserRepository;
import ac.th.ku.soa.washsystem.security.JwtUtil;
import ac.th.ku.soa.washsystem.service.dto.Userdto;
import ac.th.ku.soa.washsystem.service.dto.logindto;

@Service
public class UserService {

	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private JwtUtil jwtUitl;
	
	public Map<String, Object> read(Integer id) {
		Map<String, Object> map = new HashMap<>();
		try {
			User user = userRep.findById(id).orElse(null);

			if (user == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_EMPLOYEE");
				return map;
			} else {
				map.put("code", HttpStatus.OK.value());
				map.put("data", new Userdto.Userinfo().setId(user.getId()).setName(user.getName())
						.setTel(user.getTel()).setAddress(user.getAddress()));

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
			List<User> user = userRep.findAll();
			if (user.size() == 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_DATA");
				return map;
			}
			List<Userdto.Userinfo> userdto = user.stream()
					.map(usmap -> new Userdto.Userinfo()
							.setId(usmap.getId())
							.setName(usmap.getName())
							.setTel(usmap.getTel())
							.setAddress(usmap.getAddress()))
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
	
	public Map<String,Object> loginUser(logindto.logininfo loginreq) {
		Map<String, Object> map = new HashMap<>();
		try {
			User user = userRep.findByUserNameAndPass(loginreq.getUsername(),loginreq.getPassword());
			if(user == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_USERNAME_OR_PASS");
				return map;
			}
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "login success");
			map.put("token", jwtUitl.generateToken(user.getUserName()));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	@Transactional
	public Map<String, Object> create(Userdto.UserCerate user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(user);
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			userRep.save(new User().setName(user.getName())
								   .setTel(user.getTel())
								   .setAddress(user.getAddress())
								   .setUserName(user.getUsername())
								   .setPass(user.getPassword()));
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "cerate success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
	}
	
	@Transactional
	public Map<String, Object> update(User userreq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = userRep.findById(userreq.getId()).orElse(null);
			List<String> error = validate(new Userdto.UserCerate()
					.setName(userreq.getName())
					.setTel(userreq.getTel())
					.setAddress(userreq.getAddress())
					.setUsername(userreq.getUserName())
					.setPassword(userreq.getPass()));
			if (user == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			
			userRep.save(new User().setId(userreq.getId())
					.setName(userreq.getName())
					.setTel(userreq.getTel())
					.setAddress(userreq.getAddress())
					.setUserName(userreq.getUserName())
					.setPass(userreq.getPass()));
			map.put("code", HttpStatus.OK.value());
			map.put("data", "update seccess");
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
			User user = userRep.findById(id).orElse(null);
			if(user == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			userRep.delete(user);
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "delete success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
	}
	
	private List<String> validate(Userdto.UserCerate user) {
		List<String> error = new ArrayList<String>();
		if (user.getName() == null) {
			error.add("NOT_FOUND_Name");
		}
		if (user.getTel() == null) {
			error.add("NOT_FOUND_Tel");
		}
		if (user.getAddress() == null) {
			error.add("NOT_FOUND_Address");
		}
		if (user.getUsername() == null) {
			error.add("NOT_FOUND_Username");
		}
		if (user.getPassword() == null) {
			error.add("NOT_FOUND_Pass");
		}
		
		
		

		return error;
	}
	
	
	
}
