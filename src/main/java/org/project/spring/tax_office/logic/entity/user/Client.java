package org.project.spring.tax_office.logic.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Client extends User {

    private String name;
    private String surname;
    private String tin;

    public Client(String name, String surname, String tin) {
        super();
        this.name = name;
        this.surname = surname;
        this.tin = tin;
        super.setUserRole(UserRole.CLIENT);
    }

    public Client() {
        super();
        super.setUserRole(UserRole.CLIENT);
    }
}
