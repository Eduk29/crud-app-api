package br.com.crud.app.backend.service;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.SearchFilter;
import br.com.crud.app.backend.repository.PersonRepository;
import br.com.crud.app.backend.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return this.personRepository.findById(id);
    }

    public CustomPage<Person> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Person> page = this.personRepository.findAll(pageable);

        return new CustomPage<Person>(page);
    }

    public CustomPage<Person> findByFilter(String filter, Integer pageNumber, Integer pageSize) throws Exception {
        SearchFilter searchFilter = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Person> page = null;

        if (filter != null) {
            searchFilter = new SearchFilter(filter);
        }

        switch (searchFilter.getSearchMode()) {
            case "name":
                List<Person> persons = this.personRepository.findByNameContainsIgnoreCase(searchFilter.getSearchParameter());
                return new CustomPage(persons);

            default:
                page = this.personRepository.findAll(pageable);
                return new CustomPage<Person>(page);
        }
    }

    public CustomPage<Person> registerPerson(Person personToRegister) throws RuntimeException {
            this.validatePerson(personToRegister);
            Person registeredPerson = this.personRepository.save(personToRegister);
            return new CustomPage<>(registeredPerson);
    }

    private void validatePerson(Person person)  throws RuntimeException {
        if (ObjectUtils.isEmpty(person)) {
            throw new RuntimeException(ErrorsEnum.ERR003.getDescription());
        }

        if ((person.getAge() == null || person.getAge() == 0) ||
                (person.getName() == null || person.getName() == "") ||
                (person.getBirthday() == null || person.getBirthday() == "")) {
            throw new RuntimeException(ErrorsEnum.ERR003.getDescription());
        }
    }
}
