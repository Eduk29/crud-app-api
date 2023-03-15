package br.com.crud.app.backend.repository;

import br.com.crud.app.backend.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
import java.util.Optional;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, JpaRepository<Person, Long> {
    List<Person> findAll();
    Optional<Person> findById(Long id);
    Page<Person> findAll(Pageable pageable);
    Page<Person> findByNameContainsIgnoreCase(Pageable pageble, String name);
    Page<Person> findByAge(Pageable pageable, Integer age);
    Page<Person> findByBirthdayContains(Pageable pageable, String birthday);
    boolean existsById(Long id);
    void deleteById(Long id);
}
