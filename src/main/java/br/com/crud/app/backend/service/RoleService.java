package br.com.crud.app.backend.service;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.*;
import br.com.crud.app.backend.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.crud.app.backend.model.SearchFilter;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        List<Role> roles = this.roleRepository.findAll();
        for (int i = 0; i < roles.size(); i++) {
            this.removeUserObject(roles.get(i));
            this.removeRoleListObject(roles.get(i));
        }
        return roles;
    }

    public Role findById(Long id) {
        Role role = this.roleRepository.findById(id).get();
        this.removeUserObject(role);
        this.removeRoleListObject(role);
        return role;
    }

    public List<Role> findByFilter(String filter) throws Exception {
        SearchFilter searchFilter = null;
        List<Role> roles = null;

        if (filter != null) {
            searchFilter = new SearchFilter(filter);
        }

        switch (searchFilter.getSearchMode()) {
            case "code":
                roles = this.roleRepository.findByCode(searchFilter.getSearchParameter());
                for (int i = 0; i < roles.size(); i++) {
                    this.removeUserObject(roles.get(i));
                    this.removeRoleListObject(roles.get(i));
                }
                return roles;

            default:
                return this.roleRepository.findAll();
        }

    }

    public Role registerRole(Role roleToRegister) throws RuntimeException {
        this.validateRole(roleToRegister);

        Role registeredRole = this.roleRepository.save(roleToRegister);
        return registeredRole;
    }

    public void removeById(Long id) throws RuntimeException {
        if (!this.roleRepository.existsById(id)) {
            throw new RuntimeException(ErrorsEnum.ERR008.getDescription());
        }
        this.roleRepository.deleteById(id);
    }

    public Role updateById(Long id, Role role) {
        if (!this.roleRepository.existsById(id)) {
            throw new RuntimeException(ErrorsEnum.ERR008.getDescription());
        }

        this.validateRole(role);
        role.setId(id);

        return this.roleRepository.save(role);
    }

    protected void validateRole(Role role)  throws RuntimeException {
        if (ObjectUtils.isEmpty(role)) {
            throw new RuntimeException(ErrorsEnum.ERR009.getDescription());
        }

        if ((role.getCode() == null || role.getCode() == "") ||
                (role.getName() == null || role.getName() == "") ||
                (role.getDescription() == null || role.getDescription() == "")) {
            throw new RuntimeException(ErrorsEnum.ERR009.getDescription());
        }

        if (this.roleRepository.existsByCode(role.getCode())){
            throw new RuntimeException(ErrorsEnum.ERR010.getDescription());
        }
    }

    private void removeUserObject(Role role) {
        role.setUsers(null);
    }

    private void removeRoleListObject(Role role) {
        role.setRoleList(null);
    }
}
