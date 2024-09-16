package ac.th.ku.soa.washsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.th.ku.soa.washsystem.entity.Watertmp;
import ac.th.ku.soa.washsystem.repository.WatertmpRepository;
import ac.th.ku.soa.washsystem.service.dto.Watertmpdto;


@Service
public class WatertmpService {
	
	@Autowired
	 private WatertmpRepository waterRep;
	 
	 public Map<String, Object> read(Integer id) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				Watertmp watertmp = waterRep.findById(id).orElse(null);
				if (watertmp == null) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", "NOT_FOUND_ID");
					return map;
				}
				map.put("code", HttpStatus.OK.value());
				map.put("data", watertmp);
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
				List<Watertmp> watertmp = waterRep.findAll();
				if (watertmp.size() == 0) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", "NOT_FOUND_DATA");
					return map;
				}
				map.put("code", HttpStatus.OK.value());
				map.put("data", watertmp);
				return map;
			} catch (Exception e) {
				map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
				map.put("msg", e);
				return map;
			}

		}
	 
	 
	 @Transactional
		public Map<String, Object> create(Watertmpdto.Watertmpinfo waterreq) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				List<String> error = validate(waterreq);
				if (error.size() > 0) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", String.join(",", error));
					return map;
				}
				waterRep.save(new Watertmp().setName(waterreq.getName()).setPrice(waterreq.getPrice()));
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "cerate success");
				return map;
			} catch (Exception e) {
				map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
				map.put("msg", e);
				return map;
			}
		}
	 
	 
		public Map<String, Object> update(Watertmp waterreq) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				List<String> error = validate(
						new Watertmpdto.Watertmpinfo().setName(waterreq.getName()).setPrice(waterreq.getPrice()));
				Watertmp water = waterRep.findById(waterreq.getId()).orElse(null);
				if (water == null) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", "NOT_FOUND_ID");
					return map;
				}
				if (error.size() > 0) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", String.join(",", error));
					return map;
					}
				waterRep.save(new Watertmp()
						.setId(waterreq.getId())
						.setName(waterreq.getName()).setPrice(waterreq.getPrice()));
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
				Watertmp water = waterRep.findById(id).orElse(null);
				if(water == null) {
					map.put("code", HttpStatus.NOT_FOUND.value());
					map.put("msg", "NOT_FOUND_ID");
					return map;
				}
				waterRep.delete(water);
				map.put("code", HttpStatus.OK.value());
				map.put("msg", "delete success");
				return map;
			} catch (Exception e) {
				map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
				map.put("msg", e);
				return map;
			}
			
		}
		
		
		
		private List<String> validate(Watertmpdto.Watertmpinfo water) {
			List<String> error = new ArrayList<String>();
			if (water.getName() == null) {
				error.add("NOT_FOUND_Name");
			}
			if (water.getPrice() == null) {
				error.add("NOT_FOUND_Price");
			}

			return error;
		}


}
