package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.service.PersonService;
import br.com.crud.app.backend.utils.RoleUtils;
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
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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

            return AdjustRoleData(page);
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

            return AdjustRoleData(page);
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

            Optional<Person> personResponse = this.personService.findById(id);
            personResponse.get().setUser(RoleUtils.removeRoleDataFromUser(personResponse.get().getUser(), true));

            if(personResponse.isEmpty()) {
                return new ResponseEntity<>(ErrorsEnum.ERROO1.getDescription(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(personResponse, HttpStatus.OK);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "new", consumes = "application/json")
    @ApiOperation(value = "Register a new person.")
    public ResponseEntity<?> registerPerson(@RequestBody Person person) {
        try{
            CustomPage<Person> page = this.personService.registerPerson(person);

            if (page.getContent() != null) {
                List<Person> persons = this.removeRoleDataFromPersonList(page.getContent());
                page.setContent(persons);
                return new ResponseEntity<>(page, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            CustomPage<Person> page = this.personService.updateById(person, id);

            if (page.getContent() != null) {
                List <Person> persons = this.removeRoleDataFromPersonList(page.getContent());
                page.setContent(persons);
                return new ResponseEntity<CustomPage<Person>>(page, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch(RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> AdjustRoleData(CustomPage<Person> page) {
        if (page.getContent() != null) {
            List<Person> persons = this.removeRoleDataFromPersonList(page.getContent());
            page.setContent(persons);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<Person> removeRoleDataFromPersonList(List<Person> persons) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getUser() != null) {
                RoleUtils.removeRoleDataFromUser(person.getUser(), true);
            }
        }
        return persons;
    }
}
