package org.bankapp.bankingapp.repository;

import org.bankapp.bankingapp.entity.Account;
import org.bankapp.bankingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
