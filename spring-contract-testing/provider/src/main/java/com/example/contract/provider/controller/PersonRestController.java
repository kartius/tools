package com.example.contract.provider.controller;

import com.example.common.model.Person;
import com.example.contract.provider.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonRestController {

	private final PersonService personService;

	public PersonRestController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/person/{id}")
	public Person findPersonById(@PathVariable("id") Long id) {
		return personService.findPersonById(id);
	}

	@GetMapping("/persons")
	public List<Person> findAllPersona() {
		return personService.findAllPersons();
	}
}
