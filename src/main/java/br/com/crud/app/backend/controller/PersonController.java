package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@Api(tags = "Persons REST API")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    @ApiOperation(value = "List all persons.")
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

    @GetMapping("search")
    @ApiOperation(value = "Find a specific person by search parameter.")
    public ResponseEntity<?> findByFilter(@RequestParam(value = "$pageNumber", required = false) String pageNumberParameter,
                                          @RequestParam(value = "$pageSize", required = false) String pageSizeParameter,
                                          @RequestParam(value = "$filter", required = false) String filterParameter) {
        try {
            Integer pageNumber = Integer.parseInt(pageNumberParameter);
            Integer pageSize = Integer.parseInt(pageSizeParameter);

            CustomPage<Person> page = this.personService.findByFilter(filterParameter, pageNumber, pageSize);

            if (page.getContent() != null) {
                return new ResponseEntity<>(page, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a specific person by id.")
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
    @ApiOperation(value = "Register a new person.")
    public ResponseEntity<?> registerPerson(@RequestBody Person person) {
        try{
            CustomPage<Person> page = this.personService.registerPerson(person);
            return new ResponseEntity<>(page, HttpStatus.CREATED);
        } catch (RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}/remove")
    @ApiOperation(value = "Remove a specific person.")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            this.personService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch ( RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(path = "{id}/update")
    @ApiOperation(value = "Update a specific person.")
    public ResponseEntity<?> update(@RequestBody Person person, @PathVariable("id") Long id) {
        try {
            CustomPage<Person> persons = this.personService.updateById(person, id);
            return new ResponseEntity<CustomPage<Person>>(persons, HttpStatus.OK);
        } catch(RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
