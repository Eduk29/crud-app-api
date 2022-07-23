package br.com.crud.app.backend.repository;

import br.com.crud.app.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository  extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    List<User> findAll();
}
