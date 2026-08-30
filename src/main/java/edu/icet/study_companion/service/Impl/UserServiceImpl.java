package edu.icet.study_companion.service.Impl;

import edu.icet.study_companion.dto.UpdateUserRequestDTO;
import edu.icet.study_companion.dto.UserDTO;
import edu.icet.study_companion.entity.User;
import edu.icet.study_companion.repository.UserRepository;
import edu.icet.study_companion.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setUser_name(userDTO.getUser_name());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        user.setCreated_at(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        UserDTO responseDTO = mapToDTO(savedUser);
        responseDTO.setPassword(null);
        return responseDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserRequestDTO request) {

        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (!existingUser.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        User updatedUser = userRepository.update(id, request.getUser_name(), request.getEmail());

        return mapToDTO(updatedUser);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUser_name(user.getUser_name());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreated_at(user.getCreated_at());
        return dto;
    }
}
