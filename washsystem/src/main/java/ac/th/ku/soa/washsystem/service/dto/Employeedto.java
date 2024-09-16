package ac.th.ku.soa.washsystem.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

public class Employeedto {

	
	
	
	@Accessors(chain = true)
	@Data
	public static class EmployeeCreate{
		private String name;
		private String position;
		private String username;
		private String password;
	}
	
	@Accessors(chain = true)
	@Data
	public static class Employeeupdate{
		private Integer id;
		private String name;
		private String position;
		private String username;
		private String password;
	}
	
	
	@Accessors(chain = true)
	@Data
	public static class EmployeeInfo {
		private Integer id;
		private String name;
		private String position;
	}
}
