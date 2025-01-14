package com.gbs.msauthentication.api;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}
