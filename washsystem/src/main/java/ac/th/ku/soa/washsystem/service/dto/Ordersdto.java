package ac.th.ku.soa.washsystem.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

public class Ordersdto {

	@Accessors(chain = true)
	@Data
	public static class OrdersUpdateStatus{
		private Integer id;
		private String  status;
	}
		
	@Accessors(chain = true)
	@Data
	public static class OrdersUpdateEmp{
		private Integer id;
		private Integer emp;
	}
	
	@Accessors(chain = true)
	@Data
	public static class OrdersCerate{
		private Integer user;
		private Integer watertmp;
		private Integer fabrisoftener;
		private Integer mypackage;
		private String  status;
	}
	
	@Accessors(chain = true)
	@Data
	public static class Ordersinfo{
		private Integer id;
		private String  Status;
		private Integer Iduser;
		private String  user;
		private Integer Idwatertmp;
		private String  watertmp;
		private Integer Idfabrisoftener;
		private String  fabrisoftener;
		private Integer Idmypackage;
		private String  mypackage;
		private Integer Idempbycheck;
		private String  empbycheck;
		private Integer Idempbysender;
		private String  empbysender;
		private Integer Idempbyreciever;
		private String	empbyreciever;
	}
}
