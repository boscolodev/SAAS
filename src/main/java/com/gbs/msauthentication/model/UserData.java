package com.gbs.msauthentication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String sobrenome;
    @Column(nullable = false, unique = true, name = "cpf_cnpj")
    private String cpfCnpj;
    @Column(unique = true, name = "rg_ie")
    private String rgIe;
    @Column(name = "data_nascimento")
    private String dataNascimento;
    private String telefone;
}