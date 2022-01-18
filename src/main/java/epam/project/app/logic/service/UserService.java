package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.UserDTO;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.repository.UserRepository;
import epam.project.app.infra.web.exception.AppException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUserByLogin(UserDTO userDTO) {
        User user = repository.getUserByLogin(userDTO.getLogin())
                .orElseThrow(() -> new AppException("user by login didn't find"));

        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new AppException("password is incorrect");
        }
        return user;
    }
}
