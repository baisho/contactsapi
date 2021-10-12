package hu.futureofmedia.task.contactsapi.controllers;

import hu.futureofmedia.task.contactsapi.dtos.ContactDTO;
import hu.futureofmedia.task.contactsapi.services.ContactService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ContactDTO getContactDetails(@RequestParam Long contactID) {
        logger.info("Requesting details of contact with id = " + contactID);
        ContactDTO contactDTO = contactService.getContactDetails(contactID);
        logger.info("Contact details with id = " + contactID + " is being sent to resquester.");
        return contactDTO;
    }
}
