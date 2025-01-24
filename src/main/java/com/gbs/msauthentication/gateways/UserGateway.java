package com.gbs.msauthentication.gateways;

import com.gbs.msauthentication.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGateway extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUserData_CpfCnpj(String email, String cpfCnpj);
}
