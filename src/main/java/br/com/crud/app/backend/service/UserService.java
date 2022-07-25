package br.com.crud.app.backend.service;

import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.User;
import br.com.crud.app.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
