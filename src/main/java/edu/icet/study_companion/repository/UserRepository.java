package edu.icet.study_companion.repository;

import edu.icet.study_companion.entity.User;

public interface UserRepository {

    User findById(Long id);

    User findByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);
}
