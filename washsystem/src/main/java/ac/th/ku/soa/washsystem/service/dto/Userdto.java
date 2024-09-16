package ac.th.ku.soa.washsystem.service.dto;



import lombok.Data;
import lombok.experimental.Accessors;

public class Userdto {

	
	@Accessors(chain = true)
	@Data
	public static class UserCerate{
		private String name;
		private String tel;
		private String address;
		private String username;
		private String password;
		
	}
	
	@Accessors(chain = true)
	@Data
	public static class Userinfo{
		private Integer id;
		private String name;
		private String tel;
		private String address;
		
	}
}
