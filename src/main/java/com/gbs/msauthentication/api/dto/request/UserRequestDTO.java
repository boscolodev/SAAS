package com.gbs.msauthentication.api.dto.request;

import com.gbs.msauthentication.api.dto.AddressDTO;
import com.gbs.msauthentication.api.dto.UserDataDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String status;

    @NotNull
    private UserDataDTO userData;

    @NotNull
    private Set<RoleRequestDTO> roles;

    @NotNull
    private List<AddressDTO> addresses;
}