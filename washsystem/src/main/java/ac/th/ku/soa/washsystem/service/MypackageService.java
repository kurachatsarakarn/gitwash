package ac.th.ku.soa.washsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ac.th.ku.soa.washsystem.entity.MyPackage;
import ac.th.ku.soa.washsystem.repository.MypackageRespository;
import ac.th.ku.soa.washsystem.service.dto.Mypackagedto;

@Service
public class MypackageService {

	@Autowired
	private MypackageRespository myPackRep;

	public Map<String, Object> read(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MyPackage mypack = myPackRep.findById(id).orElse(null);
			if (mypack == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			map.put("code", HttpStatus.OK.value());
			map.put("data", mypack);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("data", e);
			return map;
		}
	}

	public Map<String, Object> readAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<MyPackage> mypack = myPackRep.findAll();
			if (mypack.size() == 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_DATA");
				return map;
			}
			map.put("code", HttpStatus.OK.value());
			map.put("data", mypack);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}

	}

	@Transactional
	public Map<String, Object> create(Mypackagedto.MypackageCreate myPackagereq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(myPackagereq);
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			myPackRep.save(new MyPackage().setName(myPackagereq.getName())
					.setPrice(myPackagereq.getPrice())
					.setDry(myPackagereq.getDry())
					.setWash(myPackagereq.getWash()));
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "cerate success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e.getMessage());
			return map;
		}
	}

	public Map<String, Object> update(MyPackage mypackreq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(new Mypackagedto.MypackageCreate().setName(mypackreq.getName())
					.setPrice(mypackreq.getPrice()).setDry(mypackreq.getDry()).setWash(mypackreq.getWash()));
			MyPackage myPack = myPackRep.findById(mypackreq.getId()).orElse(null);
			if (myPack == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			myPackRep.save(new MyPackage().setId(mypackreq.getId()).setName(mypackreq.getName()).setPrice(mypackreq.getPrice())
					.setWash(mypackreq.getWash()).setDry(mypackreq.getDry()));
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "update success");
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
			MyPackage mypack = myPackRep.findById(id).orElse(null);
			if(mypack == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			myPackRep.delete(mypack);
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "delete success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
		
	}
	
	

	private List<String> validate(Mypackagedto.MypackageCreate myPackage) {
		List<String> error = new ArrayList<String>();
		if (myPackage.getName() == null) {
			error.add("NOT_FOUND_Name");
		}
		if (myPackage.getPrice() == null) {
			error.add("NOT_FOUND_Price");
		}
		if (myPackage.getWash() == null) {
			error.add("NOT_FOUND_Wash");
		}
		if (myPackage.getDry() == null) {
			error.add("NOT_FOUND_Dry");
		}

		return error;
	}

}
