package org.project.spring.tax_office.logic.repository;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.repository.extractor.UserResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserResultSetExtractor userResultSetExtractor;

    private static final String SELECT_USER_BY_LOGIN = "select * from user left join client on user.id=client.id where user.login= ?;";

    public Optional<User> getUserByLogin(String login) {
        return jdbcTemplate.query(SELECT_USER_BY_LOGIN,
                preparedStatement -> preparedStatement.setString(1, login),
                userResultSetExtractor);
    }
}
