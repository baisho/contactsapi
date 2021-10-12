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
@RequestMapping(path = "/contact")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class.getName());

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/list")
    public List<ContactDTO> getContactList(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastName") String sortBy) throws Exception {

        System.out.println(org.hibernate.Version.getVersionString());

        logger.info("Requesting contact list");
        try {
            List<ContactDTO> contactDTOList = contactService.getContactList(pageNo, pageSize, sortBy);
            logger.info("Contact list is being sent to resquester.");
            return contactDTOList;
        } catch (Exception ex) {
            throw ex;
        }

    }
}
