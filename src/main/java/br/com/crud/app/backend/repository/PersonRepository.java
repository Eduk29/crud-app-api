package br.com.crud.app.backend.repository;

import br.com.crud.app.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAll();
    Optional<Person> findById(Long id);
}
