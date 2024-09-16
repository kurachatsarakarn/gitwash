package ac.th.ku.soa.washsystem;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;

public class testdb {

	@PersistenceContext
	private EntityManager entity;
	
	public void testdb() {
		List<Tuple> result = entity.createNativeQuery("SELECT * FROM employee", Tuple.class).getResultList();
		
		for(Tuple  Tuple :result) {
			 String name = Tuple.get("name",String.class);
			 System.out.print(name);
		}
	}
	
}
