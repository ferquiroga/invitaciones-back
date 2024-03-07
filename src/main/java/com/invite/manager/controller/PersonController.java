package com.invite.manager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.invite.manager.exception.ResourceNotFoundException;
import com.invite.manager.model.Person;
import com.invite.manager.repository.PersonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/people")
    public List<Person> getPeople() {
        return personRepository.findAll();
    }
    
    @PostMapping("/people")
    public Person savePerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException(("Resource not found, id: " + id)));
        return ResponseEntity.ok(person);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personData) {
        Person person = personRepository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException(("Resource not found, id: " + id)));

        person.setName(personData.getName());
        person.setSurname(personData.getSurname());
        person.setEmail(personData.getEmail());
        person.setConfirmed(personData.getConfirmed());

        Person updated = personRepository.save(person);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/people/{id}")
	public ResponseEntity<Map<String,Boolean>> deletePerson(@PathVariable Long id){
		Person person = personRepository.findById(id)
				            .orElseThrow(() -> new ResourceNotFoundException("Resource not found, id: " + id));
		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete",Boolean.TRUE);
		return ResponseEntity.ok(response);
    }
}
