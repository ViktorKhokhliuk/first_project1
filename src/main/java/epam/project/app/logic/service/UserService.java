package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.UserDTO;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.exception.UserException;
import epam.project.app.logic.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUserByLogin(UserDTO userDTO) {
        User user = repository.getUserByLogin(userDTO.getLogin())
                .orElseThrow(() -> new UserException("user by login didn't find"));

        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new UserException("password is incorrect");
        }
        return user;
    }
}
