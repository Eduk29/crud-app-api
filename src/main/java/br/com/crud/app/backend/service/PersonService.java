package br.com.crud.app.backend.service;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.model.CustomPage;
import br.com.crud.app.backend.model.Person;
import br.com.crud.app.backend.model.SearchFilter;
import br.com.crud.app.backend.repository.PersonRepository;
import br.com.crud.app.backend.utils.RoleUtils;
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

    @Autowired
    private UserService userService;

    public List<Person> findAll() {
        List<Person> personsResponse = this.personRepository.findAll();
        List<Person> persons = this.removeRoleDataFromPersonList(personsResponse);
        return persons;
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
                page = this.personRepository.findByNameContainsIgnoreCase(pageable, searchFilter.getSearchParameter());
                return new CustomPage(page);

            case "age":
                page = this.personRepository.findByAge(pageable, Integer.parseInt(searchFilter.getSearchParameter()));
                return new CustomPage(page);

            case "birthday":
                page = this.personRepository.findByBirthdayContains(pageable, searchFilter.getSearchParameter());
                return new CustomPage(page);

            default:
                page = this.personRepository.findAll(pageable);
                return new CustomPage<Person>(page);
        }
    }

    public CustomPage<Person> registerPerson(Person personToRegister) throws RuntimeException {
            this.validatePerson(personToRegister);
            this.userService.validateUser(personToRegister.getUser());
            this.userService.createLoginCount(personToRegister.getUser());

            Person registeredPerson = this.personRepository.save(personToRegister);
            return new CustomPage<>(registeredPerson);
    }

    public void removeById(Long id) throws RuntimeException {
        if (!this.personRepository.existsById(id)) {
            throw new RuntimeException(ErrorsEnum.ERROO1.getDescription());
        }
        this.personRepository.deleteById(id);
    }

    public CustomPage<Person> updateById(Person person, Long id) throws RuntimeException {
        if (!this.personRepository.existsById(id)) {
            throw new RuntimeException(ErrorsEnum.ERROO1.getDescription());
        }

        this.validatePerson(person);

        person.setId(id);
        person = this.personRepository.save(person);

        CustomPage<Person> customPage = new CustomPage<Person>(person);
        return customPage;
    }

    private List<Person> removeRoleDataFromPersonList(List<Person> persons) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getUser() != null) {
                RoleUtils.removeRoleDataFromUser(person.getUser(), true);
            }
        }
        return persons;
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
