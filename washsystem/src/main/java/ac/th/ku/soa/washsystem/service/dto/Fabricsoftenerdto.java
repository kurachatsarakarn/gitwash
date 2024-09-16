package ac.th.ku.soa.washsystem.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

public class Fabricsoftenerdto {

	@Accessors(chain = true)
	@Data
	public static class FabricsoftenerCarate{
		private String name;
		private Integer price;
	}
	
	
	
	
	@Accessors(chain = true)
	@Data
	public static class Fabricsoftenerinfo{
		private Integer id;
		private String name;
		private Integer price;
	}
}
