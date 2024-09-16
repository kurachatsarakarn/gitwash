package ac.th.ku.soa.washsystem.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

public class Watertmpdto {
	
	
	@Accessors(chain = true)
	@Data
	public static class Watertmpinfo{
		private String name;
		private Integer price;

	}
}
