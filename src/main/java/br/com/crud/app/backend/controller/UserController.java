package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.model.User;
import br.com.crud.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllPaginated(@RequestParam(value = "$pageNumber", required = false) String pageNumberParameter, @RequestParam(value = "$pageSize", required = false) String pageSizeParameter) {
        List<User> users = this.userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
