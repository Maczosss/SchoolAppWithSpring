package com.example.school.service;

import com.example.school.constants.SchoolConstants;
import com.example.school.model.Contact;
import com.example.school.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        System.out.println("ContactService Bean initialized");
    }

    public boolean saveMessageDetails(Contact contact) {
        var isSaved = false;
        contact.setStatus(SchoolConstants.OPEN);
        var savedContact = contactRepository.save(contact);
        if (savedContact.getContactId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findByStatus(SchoolConstants.OPEN);
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize = 5;
        var pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
//        var msgPage = contactRepository.findByStatus(
        var msgPage = contactRepository.findOpenMsgs(
                //in native pagination can't use sorting algorithms
//        var msgPage = contactRepository.findOpenMsgsNative(
                SchoolConstants.OPEN, pageable
        );
        return msgPage;
    }

//    public boolean updateMsgStatus(int contactId) {
//        var isUpdated = false;
//        var contact = contactRepository.findById(contactId);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(SchoolConstants.CLOSE);
//        });
//        var updatedContact = contactRepository.save(contact.get());
//        if (updatedContact.getUpdatedBy() != null) {
//            isUpdated = true;
//        }
//        return isUpdated;
//    }

    public boolean updateMsgStatus(int contactId) {
        var isUpdated = false;
//        var rows = contactRepository.updateStatusByID(SchoolConstants.CLOSE, contactId);
//        var rows = contactRepository.updateMsgStatus(SchoolConstants.CLOSE, contactId);
        var rows = contactRepository.updateMsgStatusNative(SchoolConstants.CLOSE, contactId);
        if (rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
