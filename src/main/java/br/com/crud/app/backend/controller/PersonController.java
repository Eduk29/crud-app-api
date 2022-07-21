package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.utils.CustomPage;
import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllPaginated(@RequestParam(value = "$pageNumber", required = false) String pageNumberParameter,
                                              @RequestParam(value = "$pageSize", required = false) String pageSizeParameter) {
        if (pageNumberParameter == null || pageSizeParameter == null) {
            List<Person> persons = this.personService.findAll();
            return new ResponseEntity<>(persons, HttpStatus.OK);
        }

        try {
            Integer pageNumber = Integer.parseInt(pageNumberParameter);
            Integer pageSize = Integer.parseInt(pageSizeParameter);

            CustomPage<Person> page = this.personService.findAllPaginated(pageNumber, pageSize);

            if (page.getContent() != null) {
                return new ResponseEntity<>(page, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String idParameter) {
        try {
            Long id = Long.parseLong(idParameter);

            Optional<Person> person = this.personService.findById(id);
            if(person.isEmpty()) {
                return new ResponseEntity<>(ErrorsEnum.ERROO1.getDescription(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "new", consumes = "application/json")
    public ResponseEntity<?> registerPerson(@RequestBody Person person) {
        try{
            CustomPage<Person> page = this.personService.registerPerson(person);
            return new ResponseEntity<>(page, HttpStatus.CREATED);
        } catch (RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
