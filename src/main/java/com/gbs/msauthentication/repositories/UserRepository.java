package com.gbs.msauthentication.repositories;

import com.gbs.msauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUserData_CpfCnpj(String email, String cpfCnpj);
}
