package edu.icet.study_companion.service;

import edu.icet.study_companion.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long id);

    UserDTO registerUser(UserDTO userDTO);

    UserDTO register(UserDTO userDTO);
}
