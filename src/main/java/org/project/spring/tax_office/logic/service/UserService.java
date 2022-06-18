package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.UserAuthorizationDto;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.exception.UserException;
import org.project.spring.tax_office.logic.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByLogin(UserAuthorizationDto userLoginDto) {
        return userRepository.getUserByLogin(userLoginDto.getLogin())
                .filter(user -> user.getPassword().equals(userLoginDto.getPassword()))
                .orElseThrow(() -> new UserException("login or password is incorrect"));
    }
}
