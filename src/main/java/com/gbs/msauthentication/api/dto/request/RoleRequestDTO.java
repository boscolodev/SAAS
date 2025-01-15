package com.gbs.msauthentication.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String role;

}