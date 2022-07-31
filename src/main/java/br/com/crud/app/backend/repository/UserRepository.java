package br.com.crud.app.backend.repository;

import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById(Long id);
    Page<User> findAll(Pageable pageable);
    List<User> findByUsernameContainsIgnoreCase(String username);
    Boolean existsUsersByUsername(String username);
}
