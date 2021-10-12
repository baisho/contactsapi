package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
    
    public Page<Contact> findByStatus(Status status, Pageable paging);
    public Page<Contact> findByStatusOrderByLastNameAsc(Status status, Pageable paging);
    public Contact findById(Integer id);

//    List<Contact> findAllByOrderByLastNameAsc();
//
//    List<Contact> findByEmailOrderByLastNameAsc(String email);
//    
//    //Pageable sortedByName = PageRequest.of(0, 3, Sort.by("name"));
//
//    @Query(
//            value = "SELECT * FROM contact WHERE status = 'ACTIVE' ORDER BY last_name;",
//            nativeQuery = true)
//    List<Contact> findByStatusOrderByLastNameAsc();
//    //List<Contact> findByStatus(String active);
}
