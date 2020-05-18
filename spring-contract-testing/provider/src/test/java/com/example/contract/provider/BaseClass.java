package com.example.contract.provider;

import com.example.contract.provider.controller.PersonRestController;
import com.example.contract.provider.service.PersonService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public abstract class BaseClass {

	@Autowired
	PersonRestController personRestController;

	@MockBean
	PersonService personService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(personRestController);
	}

}
