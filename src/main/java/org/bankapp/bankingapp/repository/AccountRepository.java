package org.bankapp.bankingapp.repository;

import org.bankapp.bankingapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(int accountNumber);
    boolean existsById(Long id);
    Optional<Account> findById(Long id);
    List<Account> findAll();
}
