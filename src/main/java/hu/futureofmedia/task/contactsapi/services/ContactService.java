package hu.futureofmedia.task.contactsapi.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import hu.futureofmedia.task.contactsapi.dtos.CompanyDTO;
import hu.futureofmedia.task.contactsapi.dtos.ContactDTO;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.enums.Status;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class.getName());

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<ContactDTO> getContactList(Integer pageNo, Integer pageSize) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Contact> pagedResult = contactRepository.findByStatusOrderByLastNameAsc(Status.ACTIVE, paging);
//        if (pageNo >= pagedResult.getTotalPages()) {
//            int maxPageNo = pagedResult.getTotalPages() - 1;
//            throw new Exception("Page number has been exceeded the maximum number of pages, which is " + maxPageNo);
//        }
        logger.info("Calling contacts.");
        List<ContactDTO> contactDTOs = new ArrayList<>();
        Iterable<Contact> contacts = pagedResult.getContent();
        for (Contact contact : contacts) {
            contactDTOs.add(setContactDTO(false, contact));
        }
        logger.info(contactDTOs.size() + " contacts succesfully read in db.");
        return contactDTOs;
    }

    public ContactDTO getContactDetails(Long contactID) {
        logger.info("Calling contact details.");
        Optional<Contact> result = contactRepository.findById(contactID);
        Contact contact = result.orElse(null);
        logger.info("Contact detials with id = " + contactID + " succesfully read in db.");
        //return setContactDTO(true, contact);
        CompanyDTO cDTO = new CompanyDTO(); // xxx ezt majd megcsinalni
        ContactDTO ez = new ContactDTO();
        ez.setCompany(cDTO);
        return ez;
    }

    private ContactDTO setContactDTO(boolean forDetails, Contact contact) {
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
        ctDTO.setStatus(contact.getStatus());
        if (forDetails) {
            ctDTO.setNote(contact.getNote());
            ctDTO.setDateCreation(contact.getDateCreation());
            ctDTO.setDateLastModify(contact.getDateLastModify());
        }
        return ctDTO;
    }

    public ResponseEntity<String> saveContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        // Checking empty values
        if (null == contactDTO.getLastName() || null == contactDTO.getFirstName() || null == contactDTO.getEmail()
                || null == contactDTO.getCompany() || null == contactDTO.getCompany().getId() || null == contactDTO.getCompany().getName()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("One or more form data are missing (CODE 403)\n");
        }

        if ("".equals(contactDTO.getLastName()) || "".equals(contactDTO.getFirstName()) || "".equals(contactDTO.getEmail())
                || 0L == contactDTO.getCompany().getId() || "".equals(contactDTO.getCompany().getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("One or more form data are missing (CODE 403)\n");
        }
        // Checking email form validity
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contactDTO.getEmail());
        if (!matcher.matches()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email address is not valid (CODE 403)\n");
        }

        // Checking phone number
        if (contactDTO.getPhone() != null) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                PhoneNumber number = phoneUtil.parse(contactDTO.getPhone(), "");
                int countryCode = number.getCountryCode();
                String formattedNumber = phoneUtil.format(number, PhoneNumberFormat.E164);
                if (!formattedNumber.equals(contactDTO.getPhone())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Telephone number is not in E-164 format (CODE 403)\n");
                }
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }
        }

        // Checking company
        Long companyID = contactDTO.getCompany().getId();
        if (null == companyRepository.findById(companyID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid company (CODE 403)\n");
        }

        // Checking status
        Status status = contactDTO.getStatus();
        if (status != null && !EnumUtils.isValidEnum(Status.class, status.name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid status (CODE 403)\n");
        }
        
        //JSoup.parse();

        contact.setLastName(contactDTO.getLastName());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setEmail(contactDTO.getEmail());
        Company company = new Company();
        company.setId(contactDTO.getCompany().getId());
        company.setName(contactDTO.getCompany().getName());
        contact.setCompanyId(company);
        contact.setStatus(Status.ACTIVE);
        contact.setPhone(contactDTO.getPhone());
        contact.setNote(contactDTO.getNote());
        contact.setDateCreation(LocalDateTime.now());
        contact.setDateLastModify(LocalDateTime.now());

        contactRepository.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("New contact has been created (CODE 201)\n");
    }
}
