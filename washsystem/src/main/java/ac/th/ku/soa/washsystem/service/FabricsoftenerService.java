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

import ac.th.ku.soa.washsystem.entity.Fabricsoftener;
import ac.th.ku.soa.washsystem.repository.FabricsoftenerRepository;
import ac.th.ku.soa.washsystem.service.dto.Fabricsoftenerdto;

@Service
public class FabricsoftenerService {

	@Autowired
	private FabricsoftenerRepository FabRep;

	public Map<String, Object> read(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Fabricsoftener fab = FabRep.findById(id).orElse(null);
			if (fab == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			map.put("code", HttpStatus.OK.value());
			map.put("data", fab);
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
			List<Fabricsoftener> fabricsoftener = FabRep.findAll();
			if (fabricsoftener.size() == 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_DATA");
				return map;
			}
			List<Fabricsoftenerdto.FabricsoftenerCarate> Fab = fabricsoftener.stream()
					.map(fabmap -> new Fabricsoftenerdto.FabricsoftenerCarate().setName(fabmap.getName())
							.setPrice(fabmap.getPrice()))
					.collect(Collectors.toList());
			map.put("code", HttpStatus.OK.value());
			map.put("data", Fab);
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}

	}

	@Transactional
	public Map<String, Object> create(Fabricsoftenerdto.FabricsoftenerCarate Fab) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(Fab);
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
			}
			FabRep.save(new Fabricsoftener().setName(Fab.getName()).setPrice(Fab.getPrice()));
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "cerate success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
	}

	public Map<String, Object> update(Fabricsoftenerdto.Fabricsoftenerinfo Fabreq) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> error = validate(
					new Fabricsoftenerdto.FabricsoftenerCarate().setName(Fabreq.getName()).setPrice(Fabreq.getPrice()));
			Fabricsoftener fab = FabRep.findById(Fabreq.getId()).orElse(null);
			if (fab == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			if (error.size() > 0) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", String.join(",", error));
				return map;
				}
			FabRep.save(new Fabricsoftener().setName(Fabreq.getName()).setPrice(Fabreq.getPrice()));
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
			Fabricsoftener fab = FabRep.findById(id).orElse(null);
			if(fab == null) {
				map.put("code", HttpStatus.NOT_FOUND.value());
				map.put("msg", "NOT_FOUND_ID");
				return map;
			}
			FabRep.delete(fab);
			map.put("code", HttpStatus.OK.value());
			map.put("msg", "delete success");
			return map;
		} catch (Exception e) {
			map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("msg", e);
			return map;
		}
		
	}

	private List<String> validate(Fabricsoftenerdto.FabricsoftenerCarate Fab) {
		List<String> error = new ArrayList<String>();
		if (Fab.getName() == null) {
			error.add("NOT_FOUND_Name");
		}
		if (Fab.getPrice() == null) {
			error.add("NOT_FOUND_Price");
		}

		return error;
	}

}
