package edu.icet.study_companion.service.Impl;

import edu.icet.study_companion.dto.UserDTO;
import edu.icet.study_companion.entity.User;
import edu.icet.study_companion.repository.UserRepository;
import edu.icet.study_companion.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id);

        UserDTO userDTO = new UserDTO(

                user.getId(),
                user.getUser_name(),
                user.getEmail(),
                null,
                user.getRole(),
                user.getCreated_at()
        );

        return userDTO;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUser_name(userDTO.getUser_name());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
        user.setCreated_at(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getId(),
                savedUser.getUser_name(),
                savedUser.getEmail(),
                null,
                savedUser.getRole(),
                savedUser.getCreated_at()
        );
    }
}
