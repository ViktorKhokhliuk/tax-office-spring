package org.project.spring.tax_office.logic.repository.rowmapper;

import org.project.spring.tax_office.logic.entity.user.Client;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getLong("id"));
        client.setLogin(resultSet.getString("login"));
        client.setName(resultSet.getString("name"));
        client.setSurname(resultSet.getString("surname"));
        client.setTin(resultSet.getString("tin"));
        return client;
    }
}
