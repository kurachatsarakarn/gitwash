package ac.th.ku.soa.washsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.th.ku.soa.washsystem.entity.Employee;
import ac.th.ku.soa.washsystem.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	User findByUserName(String userName);
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName AND u.pass = :pass")
	User findByUserNameAndPass(@Param("userName") String userName, @Param("pass") String pass);
}
