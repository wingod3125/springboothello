package springboot.example.repepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.example.dao.Department;

/**
 * Created by WG on 2017/7/15.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{
}
