package com.demo.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.spring.model.Employee;
import com.demo.spring.model.PoliceStation;
import com.demo.spring.model.User;
import com.demo.spring.service.PoliceStationService;
import com.demo.spring.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {

	@Autowired UserService userService;
	@Autowired PoliceStationService policeStationService;
	
	@Before
    public void initDb() {
		PoliceStation p=new PoliceStation();
        

		p.setName("Khairpur Mir's");
		policeStationService.savePoliceStation(p);
		User user=new User();
        user.setFirstName("fareed");
        user.setLastName("mahar");
        user.setEmail("fareed@gmail.com");
        user.setPassword("123456");
        
        Employee e=new Employee();
        e.setFirstName("saeed");
        e.setLastName("mahar");
        e.setEmail("saeed@gmail.com");
        e.setPassword("123456");
       
        userService.save(user);
        //userService.save(user1);
    }
	
	@Test
	public void contextLoads() {
	}

}
