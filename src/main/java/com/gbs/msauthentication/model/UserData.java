package com.gbs.msauthentication.model;


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
    private String cpfCnpj;
    private String rgIe;
    private String dataNascimento;
    private String telefone;

}
