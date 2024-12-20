package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.User;
import br.com.crud.app.backend.service.UserService;
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
@RequestMapping("/users")
@Api(tags = "Users REST API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @ApiOperation(value = "List all users.")
    public ResponseEntity<?> findAllPaginated(
            @RequestParam(value = "$pageNumber", required = false) String pageNumberParameter,
            @RequestParam(value = "$pageSize", required = false) String pageSizeParameter) {

        if (pageNumberParameter == null || pageSizeParameter == null) {
            List<User> usersResponse = this.userService.findAll();
            List<User> users = this.removeRoleDataFromUserList(usersResponse);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        try {
            Integer pageNumber = Integer.parseInt(pageNumberParameter);
            Integer pageSize = Integer.parseInt(pageSizeParameter);

            CustomPage<User> page = this.userService.findAllPaginated(pageNumber, pageSize);

            if (page.getContent() != null) {
                List<User> usersResponse = page.getContent();
                List<User> user = this.removeRoleDataFromUserList(usersResponse);
                page.setContent(user);
                return new ResponseEntity<>(page, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a specific user by id.")
    public ResponseEntity<?> findById(@PathVariable("id") String idParameter) {
        try {
            Long id = Long.parseLong(idParameter);

            CustomPage<User> page = this.userService.findById(id);
            if(page.getContent().isEmpty()) {
                return new ResponseEntity<>(ErrorsEnum.ERR004.getDescription(), HttpStatus.BAD_REQUEST);
            }

            List<User> usersResponse = page.getContent();
            List<User> users = this.removeRoleDataFromUserList(usersResponse);
            page.setContent(users);

            return new ResponseEntity<>(page, HttpStatus.OK);
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

            CustomPage<User> page = this.userService.findByFilter(filterParameter, pageNumber, pageSize);

            if (page.getContent() != null) {
                List<User> usersResponse = page.getContent();
                List<User> users = this.removeRoleDataFromUserList(usersResponse);
                page.setContent(users);
                return new ResponseEntity<>(page, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private List<User> removeRoleDataFromUserList(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            this.removeRoleFromUser(users.get(i));
        }
        return users;
    }

    private User removeRoleFromUser(User user) {
        return RoleUtils.removeRoleDataFromUser(user, true);
    }
}
