package hu.futureofmedia.task.contactsapi.dtos;
import java.io.Serializable;

public class CompanyDTO implements Serializable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
