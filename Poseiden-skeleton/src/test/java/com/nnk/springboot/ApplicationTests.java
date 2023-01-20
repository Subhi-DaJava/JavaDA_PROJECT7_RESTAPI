package com.nnk.springboot;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.controllers.UserController;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Whenever you open up your Spring Boot web application,
 * you load an instance of the WebApplicationContext, which extends to your ApplicationContext.
 * WebApplicationContext is a library that readies your servlets to create an instance of a web application.
 * The ApplicationContext runs your Spring Boot web application from your main() method in your Application.java file.
 * A test like @WebMvcTest injects the dependencies for the web layer and loads the WebApplicationContext.
 * "@SpringBootTest" integrates your application with the web layer, so it loads both the WebApplicationContext and the ApplicationContext.
 *
 * the @ExtendWith(SpringExtension.class). The @ExtendWith notation adds JUnit 5’s extensive architecture to use for your tests.
 * One of these is the SpringExtension class, which adds the test context. In short, it adds the dependencies to your tests, so you don’t have to.
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationTests {
	@Autowired
	private LoginController loginController;
	@Autowired
	private UserController userController;
	@Autowired
	private BidListController bidListController;
	@Autowired
	private HomeController homeController;
	@Test
	void contextLoads(){
		assertThat(loginController).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(bidListController).isNotNull();
		assertThat(homeController).isNotNull();
	}
}
