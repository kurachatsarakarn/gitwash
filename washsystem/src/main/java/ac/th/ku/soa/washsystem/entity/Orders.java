package ac.th.ku.soa.washsystem.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "Orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empbycheck")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee empbycheck; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empbysender")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee empbysender;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empbyreciever")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee empbyreciever;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "watertmp")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Watertmp watertmp;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fabrisoftener")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Fabricsoftener fabrisoftener;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mypackage")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MyPackage mypackage;
	
	@Column(name = "status")
	private String status;
	
}
