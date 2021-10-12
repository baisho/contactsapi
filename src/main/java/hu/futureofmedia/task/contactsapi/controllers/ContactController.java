package hu.futureofmedia.task.contactsapi.controllers;

import hu.futureofmedia.task.contactsapi.dtos.ContactDTO;
import hu.futureofmedia.task.contactsapi.services.ContactService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class.getName());

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/list")
    public List<ContactDTO> getContactList(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) throws Exception {
        logger.info("Requesting contact list");
        try {
            List<ContactDTO> contactDTOList = contactService.getContactList(pageNo, pageSize);
            logger.info("Contact list is being sent to resquester.");
            return contactDTOList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping(path = "/details")
    public ContactDTO getContactDetails(@RequestParam Long id) {
        logger.info("Requesting details of contact with id = " + id);
        ContactDTO contactDTO = contactService.getContactDetails(id);
        logger.info("Contact details with id = " + id + " is being sent to resquester.");
        return contactDTO;
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveContact(@RequestBody ContactDTO contactDTO) {
        logger.info("Saving new contact start");
        ResponseEntity<String> answer = contactService.updateOrSaveContact(contactDTO);
        logger.info(answer.toString());
        return answer;
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updateContact(@RequestBody ContactDTO contactDTO) {
        logger.info("Updating contact start");
        ResponseEntity<String> answer = contactService.updateOrSaveContact(contactDTO);
        logger.info(answer.toString());
        return answer;
    }
    
    @PutMapping(path = "/delete")
    public ResponseEntity<String> deleteContact(@RequestBody ContactDTO contactDTO) {
        logger.info("Deleting contact start");
        ResponseEntity<String> answer = contactService.deleteContact(contactDTO);
        logger.info(answer.toString());
        return answer;
    }
}
