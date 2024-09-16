package ac.th.ku.soa.washsystem.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

public class logindto {

	
	@Accessors(chain = true)
	@Data
	public static class logininfo{
		private String username;
		private String password;
	}
	
}
