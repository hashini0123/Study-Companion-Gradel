package edu.icet.study_companion.repository.Impl;

import edu.icet.study_companion.entity.User;
import edu.icet.study_companion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User findById(Long id) {

        String sql = "SELECT * FROM users WHERE id=?";
        return jdbcTemplate.queryForObject(sql,( rs, rowNum) ->{
                User user = new User();
                user.setId(rs.getLong(1));
                user.setUser_name(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setRole(rs.getString(5));
                user.setCreated_at(rs.getTimestamp(6).toLocalDateTime());
                return user;
            },
            id
        );
    }

    @Override
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email=?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                    return user;
                },
                email
        );
    }

    @Override
    public User save(User user) {

        String sql = "INSERT INTO users (user_name, email, password, role, created_at) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setTimestamp(5, Timestamp.valueOf(user.getCreated_at()));
            return ps;
        }, keyHolder
        );

        Long generatedId = keyHolder.getKey().longValue();
        user.setId(generatedId);

        return user;
    }

    @Override
    public boolean existsByEmail(String email) {

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;

    }

    @Override
    public User update(Long id, String userName, String email) {

        String sql = "UPDATE users SET user_name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, userName, email, id);
        return findById(id);

    }


}