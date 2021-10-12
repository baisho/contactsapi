package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Company;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface CompanyRepository extends Repository<Company, Long> {
    public List<Company> findAll();
    public Company findById(Long id);
}
