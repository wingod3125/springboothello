import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import springboot.example.config.JpaConfiguration;
import springboot.example.dao.Department;
import springboot.example.dao.Role;
import springboot.example.dao.User;
import springboot.example.repepository.DepartmentRepository;
import springboot.example.repepository.RoleRepository;
import springboot.example.repepository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by WG on 2017/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class MysqlTest {
    private static Logger logger = (Logger) LoggerFactory.getLogger(MysqlTest.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RoleRepository roleRepository;
    @Before
    public void initDate(){
        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
        Department department = new Department();
        department.setName("开发部");
        departmentRepository.save(department);
        Assert.notNull(department.getId());
        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.notNull(role.getId());
        User user = new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDepartment(department);
        List<Role> roles = roleRepository.findAll();
        Assert.notNull(roles);
        user.setRoles(roles);
        userRepository.save(user);
        Assert.notNull(user.getId());
    }
    @Test
    public void findPage(){
        Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Page<User> page = userRepository.findAll(pageable);
        Assert.notNull(page);
        for (User user:page.getContent()) {
            logger.info("===user===");
            logger.info("user name:"+user.getName());
            logger.info("department name"+user.getDepartment().getName());
            logger.info("role name:"+user.getRoles().get(0).getName());
        }
    }
}
