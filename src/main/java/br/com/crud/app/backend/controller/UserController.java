package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.User;
import br.com.crud.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllPaginated(
            @RequestParam(value = "$pageNumber", required = false) String pageNumberParameter,
            @RequestParam(value = "$pageSize", required = false) String pageSizeParameter) {

        if (pageNumberParameter == null || pageSizeParameter == null) {
            List<User> users = this.userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        try {
            Integer pageNumber = Integer.parseInt(pageNumberParameter);
            Integer pageSize = Integer.parseInt(pageSizeParameter);

            CustomPage<User> page = this.userService.findAllPaginated(pageNumber, pageSize);

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

            CustomPage<User> page = this.userService.findById(id);
            if(page.getContent().isEmpty()) {
                return new ResponseEntity<>(ErrorsEnum.ERR004.getDescription(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }
    }


}
