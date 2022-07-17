package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<Person> users = this.personService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
