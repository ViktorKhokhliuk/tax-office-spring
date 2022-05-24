package org.project.spring.tax_office.logic.repository.user;

import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class UserResultSetExtractor implements ResultSetExtractor<Optional<User>> {

    @Override
    public Optional<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        User foundUser;
        if (resultSet.next()) {
            long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            if (role.equals("INSPECTOR")) {
                foundUser = new Inspector();
            } else {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String tin = resultSet.getString("tin");
                foundUser = new Client(name, surname, tin);
            }
            foundUser.setId(id);
            foundUser.setLogin(login);
            foundUser.setPassword(password);
            return Optional.of(foundUser);
        }
        return Optional.empty();
    }
}
