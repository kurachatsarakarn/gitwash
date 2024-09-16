package ac.th.ku.soa.washsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@Entity
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "pass")
	private String pass;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "extra")
	private Integer extra;
	
	
}
