package br.com.crud.app.backend.controller;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.Role;
import br.com.crud.app.backend.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@Api(tags = "Roles REST API")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    @ApiOperation(value = "List all roles.")
    public ResponseEntity<?> findAll() {
        List<Role> roles = this.roleService.findAll();
        CustomPage page = new CustomPage<>(roles);

        if(roles.size() == 0 ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find role by id")
    public ResponseEntity<?> findById(@PathVariable("id") String idParameter) {
        try {
            Long id = Long.parseLong(idParameter);
            Role role = this.roleService.findById(id);

            if(ObjectUtils.isEmpty(role)) {
                return new ResponseEntity<>(ErrorsEnum.ERR008.getDescription(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (NumberFormatException error) {
            return new ResponseEntity<>(ErrorsEnum.ERROO2.getDescription(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find role by filter parameter")
    public ResponseEntity<?> findByFilter(@RequestParam(value = "$filter", required = false) String filterParameter) {
        try {
            List<Role> roles = this.roleService.findByFilter(filterParameter);

            if (roles.size() != 0) {
                return new ResponseEntity<>(roles, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ApiOperation(value = "Register a new role.")
    public ResponseEntity<?> registerPerson(@RequestBody Role role) {
        try{
            Role registeredRole = this.roleService.registerRole(role);
            return new ResponseEntity<>(registeredRole, HttpStatus.CREATED);
        } catch (RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}/remove")
    @ApiOperation(value = "Remove a specific role.")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            this.roleService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch ( RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "{id}/update")
    @ApiOperation(value = "Update a specific role.")
    public ResponseEntity<?> update(@RequestBody Role role, @PathVariable("id") Long id) {
        try {
            Role roleUpdated = this.roleService.updateById(id, role);
            return new ResponseEntity<>(roleUpdated, HttpStatus.OK);
        } catch(RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
