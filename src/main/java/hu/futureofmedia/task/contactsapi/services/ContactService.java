package hu.futureofmedia.task.contactsapi.services;

import hu.futureofmedia.task.contactsapi.dtos.CompanyDTO;
import hu.futureofmedia.task.contactsapi.dtos.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class.getName());

    @Autowired
    private ContactRepository contactRepository;

    public List<ContactDTO> getContactList(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Contact> pagedResult = contactRepository.findByStatus("ACTIVE", paging);
        if (pageNo >= pagedResult.getTotalPages()) {
            int maxPageNo = pagedResult.getTotalPages() - 1;
            throw new Exception("Page number has been exceeded the maximum number of pages, which is " + maxPageNo);
        }
        logger.info("Calling contacts.");
        List<ContactDTO> contactDTOs = new ArrayList<>();
        Iterable<Contact> contacts = pagedResult.getContent();
        for (Contact contact : contacts) {
            ContactDTO ctDTO = new ContactDTO();
            ctDTO.setId(contact.getId());
            ctDTO.setLastName(contact.getLastName());
            ctDTO.setFirstName(contact.getFirstName());
            ctDTO.setEmail(contact.getEmail());
            ctDTO.setPhone(contact.getPhone());
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setId(contact.getCompanyId().getId());
            companyDTO.setName(contact.getCompanyId().getName());
            ctDTO.setCompany(companyDTO);
            contactDTOs.add(ctDTO);
        }
        logger.info(contactDTOs.size() + " contacts succesfully read in db.");
        return contactDTOs;
    }
}

//    public List<ContactDTO> getContactList2(Integer pageNo, Integer pageSize, String sortBy) {
//        logger.info("Calling contacts.");
//        List<ContactDTO> contactDTOs = new ArrayList<>();
//        Iterable<Contact> contacts = contactRepository.findByStatusOrderByLastNameAsc();
//        for (Contact contact : contacts) {
//            ContactDTO ctDTO = new ContactDTO();
//            ctDTO.setId(contact.getId());
//            ctDTO.setLastName(contact.getLastName());
//            ctDTO.setFirstName(contact.getFirstName());
//            ctDTO.setEmail(contact.getEmail());
//            ctDTO.setPhone(contact.getPhone());
//            CompanyDTO companyDTO = new CompanyDTO();
//            companyDTO.setId(contact.getCompanyId().getId());
//            companyDTO.setName(contact.getCompanyId().getName());
//            ctDTO.setCompany(companyDTO);
//            contactDTOs.add(ctDTO);
//        }
//        logger.info(contactDTOs.size() + " contacts succesfully read in db.");
//        return contactDTOs;
//    }

//
//    @Autowired
//    private AtrRepository atrRepository;
//
//    @Autowired
//    private KakNumberRepository kakRepository;
//
//    @Autowired
//    private CardTypeRepository cardTypeRepository;
//
//    @Autowired
//    private CardExtraInfoRepository cardExtraInfoRepository;
//
//    @Autowired
//    private RAtrCardExtraInfoRepository rAtrExtraInfoRepository;

//    public List<ContactDTO> getAllContacts() {
//        logger.info("Calling contacts.");
//        List<ContactDTO> contactDTOs = new ArrayList<>();
//        Iterable<Contact> contacts = contactRepository.findAllByOrderByLastNameAsc();
//        for (Contact contact : contacts) {
//            ContactDTO ctDTO = new ContactDTO();
//            ctDTO.setId(contact.getId());
//            ctDTO.setLastName(contact.getLastName());
//            ctDTO.setFirstName(contact.getFirstName());
//            ctDTO.setEmail(contact.getEmail());
//            ctDTO.setPhone(contact.getPhone());
//            CompanyDTO companyDTO = new CompanyDTO();
//            companyDTO.setId(contact.getCompanyId().getId());
//            companyDTO.setName(contact.getCompanyId().getName());
//            ctDTO.setCompany(companyDTO);
//            ctDTO.setNote(contact.getNote());
//            ctDTO.setStatus(contact.getStatus());
//            ctDTO.setDateCreation(contact.getDateCreation());
//            ctDTO.setDateLastModify(contact.getDateLastModify());
//            contactDTOs.add(ctDTO);
//        }
//        logger.info(contactDTOs.size() + " contacts succesfully read in db.");
//        return contactDTOs;
//    }

