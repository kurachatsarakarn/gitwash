package ac.th.ku.soa.washsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ac.th.ku.soa.washsystem.entity.Employee;
import ac.th.ku.soa.washsystem.entity.Orders;

public interface OrdersRespository extends JpaRepository<Orders, Integer>{
    
	@Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

	@Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.empbycheck = :empbycheck WHERE o.id = :id")
    int updateEmpByCheck(@Param("id") Integer id, @Param("empbycheck") Employee empbycheck);
	
	@Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.empbysender = :empbysender WHERE o.id = :id")
    int updateEmpBySender(@Param("id") Integer id, @Param("empbysender") Employee empbysender);
	
	@Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.empbyreciever = :empbyreciever WHERE o.id = :id")
    int updateEmpByReciever(@Param("id") Integer id, @Param("empbyreciever") Employee empbyreciever);

}
