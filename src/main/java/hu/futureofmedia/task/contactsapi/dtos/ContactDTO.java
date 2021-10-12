package hu.futureofmedia.task.contactsapi.dtos;

import hu.futureofmedia.task.contactsapi.enums.Status;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ContactDTO implements Serializable {

    private Long id;
    private String lastName; 
    private String firstName;
    private String email;
    private String phone;
    private String note;
    private Status status;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLastModify;
    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLastModify() {
        return dateLastModify;
    }

    public void setDateLastModify(LocalDateTime dateLastModify) {
        this.dateLastModify = dateLastModify;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    
}
