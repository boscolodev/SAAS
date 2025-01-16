package com.gbs.msauthentication.gateways;

import com.gbs.msauthentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleGateway extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
