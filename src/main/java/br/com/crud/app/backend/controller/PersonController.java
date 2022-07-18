package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<Person> persons = this.personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Person> person = this.personService.findById(id);
        if(person.isEmpty()) {
            return new ResponseEntity<>(ErrorsEnum.ERROO1.getDescription(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }


}
