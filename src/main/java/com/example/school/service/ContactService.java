package com.example.school.service;

import com.example.school.controller.ContactController;
import com.example.school.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        //TODO Save data do database
        log.info(contact.toString());
        return isSaved;
    }
}
