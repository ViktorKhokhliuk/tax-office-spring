package org.project.spring.tax_office.logic.repository.client;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ClientRowMapper clientRowMapper;

    private static final String SELECT_CLIENTS_BY_SEARCH_PARAMETERS = """
            select * from client join user on user.id=client.id
             where name like concat('%',?,'%') and surname like concat('%',?,'%')
             and tin like concat('%',?,'%') limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_SEARCH_PARAMETERS = """
            select count(*) from client join user on user.id=client.id
             where name like concat('%',?,'%') and surname like concat('%',?,'%')
             and tin like concat('%',?,'%');
            """;
    private static final String INSERT_USER = "INSERT INTO user (login,password,role) VALUES (?,?,?);";
    private static final String INSERT_CLIENT = "INSERT INTO client (id,name,surname,tin) VALUES (?,?,?,?);";
    private static final String SELECT_ALL_CLIENTS = "select * from client join user on user.id=client.id limit ?, 5;";
    private static final String SELECT_ALL_CLIENTS_NO_LIMIT = "select * from client join user on user.id=client.id;";
    private static final String DELETE_USER_BY_ID = "delete from user where id = ?;";
    private static final String SELECT_COUNT = "select count(*) from client join user on user.id=client.id;";
    private static final String DELETE_REPORTS_BY_CLIENT_ID = "delete from report where clientId = ?;";
    private static final String DELETE_CLIENT_BY_ID = "delete from client where id = ?;";


    public Client insertClient(ClientRegistrationDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dto.getLogin());
            preparedStatement.setString(2, dto.getPassword());
            preparedStatement.setString(3, dto.getUserRole().toString());
            return preparedStatement;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, dto.getName());
            preparedStatement.setString(3, dto.getSurname());
            preparedStatement.setString(4, dto.getTin());
            return preparedStatement;
        });

        Client client = new Client(dto.getName(), dto.getSurname(), dto.getTin());
        client.setId(id);
        return client;
    }

    public boolean deleteById(Long id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
        return true;
    }

    public List<Client> getAll(int index) {
        return jdbcTemplate.query(SELECT_ALL_CLIENTS, clientRowMapper, index);
    }

    public List<Client> getAllClientsNoLimit() {
        return jdbcTemplate.query(SELECT_ALL_CLIENTS_NO_LIMIT, clientRowMapper);
    }

    public Double getCountOfFieldsForAll() {
        return jdbcTemplate.queryForObject(SELECT_COUNT, Double.class);
    }

    public List<Client> getClientsBySearchParameters(ClientSearchDto clientSearchDto, int index) {
        return jdbcTemplate.query(SELECT_CLIENTS_BY_SEARCH_PARAMETERS, clientRowMapper,
                clientSearchDto.getName(), clientSearchDto.getSurname(), clientSearchDto.getTin(), index);
    }

    public Double getCountOfFieldsForSearchParameters(ClientSearchDto clientSearchDto) {
        return jdbcTemplate.queryForObject(SELECT_COUNT_FOR_SEARCH_PARAMETERS, Double.class,
                clientSearchDto.getName(), clientSearchDto.getSurname(), clientSearchDto.getTin());
    }
}
