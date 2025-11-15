package com.example.DTO;


import lombok.Data;
import java.util.List;

@Data
public class RutaDTO {

    private Long id;

    private List<TramoDTO> tramos;
}