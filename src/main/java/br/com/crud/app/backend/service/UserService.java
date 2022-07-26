package br.com.crud.app.backend.service;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.SearchFilter;
import br.com.crud.app.backend.model.User;
import br.com.crud.app.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public CustomPage<User> findById (Long id) {
        Optional<User> userResponse = this.userRepository.findById(id);
        User user = userResponse.get();
        return new CustomPage<User>(user);
    }

    public CustomPage<User> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = this.userRepository.findAll(pageable);

        return new CustomPage<User>(page);
    }

    public CustomPage<User> findByFilter(String filter, Integer pageNumber, Integer pageSize) throws Exception {
        SearchFilter searchFilter = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = null;
        List<User> users = null;

        if (filter != null) {
            searchFilter = new SearchFilter(filter);
        }

        switch (searchFilter.getSearchMode()) {
            case "username":
                users = this.userRepository.findByUsernameContainsIgnoreCase(searchFilter.getSearchParameter());
                return new CustomPage(users);

            default:
                page = this.userRepository.findAll(pageable);
                return new CustomPage<User>(page);
        }
    }

    public CustomPage<User> registerUser(User userToRegister) throws RuntimeException {
        this.validateUser(userToRegister);

        userToRegister.setLoginCount(0);
        User registeredUser = this.userRepository.save(userToRegister);
        return new CustomPage<>(registeredUser);
    }

    private void validateUser(User user)  throws RuntimeException {
        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException(ErrorsEnum.ERR005.getDescription());
        }

        if ((user.getUsername() == null || user.getUsername() == "") ||
                (user.getPassword() == null || user.getPassword() == "")) {
            throw new RuntimeException(ErrorsEnum.ERR005.getDescription());
        }
    }

    private void validatePersonInsideUser(User user) {
        if (user.getPerson().getId() == null || user.getPerson().getId() == 0) {
            throw new RuntimeException(ErrorsEnum.ERR006.getDescription());
        } else if ((user.getPerson().getId() != null || user.getPerson().getId() != 0))
    }

}
