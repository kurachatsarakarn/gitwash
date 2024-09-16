package ac.th.ku.soa.washsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.th.ku.soa.washsystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByuserName(String userName);

	@Query("SELECT e FROM Employee e WHERE e.userName = :userName AND e.pass = :pass")
	Employee findByUserNameAndPass(@Param("userName") String userName, @Param("pass") String password);
}
