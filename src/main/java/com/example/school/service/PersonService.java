package com.example.school.service;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Person;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        var isSaved = false;
        var role = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (person != null && person.getPersonId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }
}
