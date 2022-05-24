package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.UserLoginDto;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.exception.UserException;
import org.project.spring.tax_office.logic.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByLogin(UserLoginDto userLoginDto) {
        User user = userRepository.getUserByLogin(userLoginDto.getLogin())
                .orElseThrow(() -> new UserException("user by login didn't find"));

        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new UserException("password is incorrect");
        }
        return user;
    }
}
