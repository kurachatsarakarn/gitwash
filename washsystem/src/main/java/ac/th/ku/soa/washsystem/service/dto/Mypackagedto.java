package ac.th.ku.soa.washsystem.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

public class Mypackagedto {
	
	@Accessors(chain = true)
	@Data
	public static class Mypackageinfo{
		private Integer id;
		private String name;
		private Integer price;
		private Integer wash;
		private Integer dry;
	}
	
	
	@Accessors(chain = true)
	@Data
	public static class MypackageCreate{
		private String name;
		private Integer price;
		private Integer wash;
		private Integer dry;
	}
}
