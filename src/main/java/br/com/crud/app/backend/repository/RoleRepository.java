package br.com.crud.app.backend.repository;

import br.com.crud.app.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, JpaRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    List<Role> findByCode(String code);
    Boolean existsByCode(String code);
    void deleteById(Long id);
}
