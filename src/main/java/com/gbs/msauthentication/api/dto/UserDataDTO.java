package com.gbs.msauthentication.api.dto;

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
public class UserDataDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String nome;
    @NotEmpty
    private String sobrenome;
    @NotEmpty
    private String cpfCnpj;
    @NotEmpty
    private String rgIe;
    @NotEmpty
    private String dataNascimento;
    @NotEmpty
    private String telefone;
}
