package org.project.spring.tax_office.logic.entity.user;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Inspector extends User {

    public Inspector() {
        super();
        super.setUserRole(UserRole.INSPECTOR);
    }
}
