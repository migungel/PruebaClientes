package com.test.neoris.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String clienteid;
    private boolean estado;
}
