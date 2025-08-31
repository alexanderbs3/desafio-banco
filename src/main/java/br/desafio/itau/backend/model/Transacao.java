package br.desafio.itau.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Transacao {
    private double valor;
    private OffsetDateTime dataHora;
    private Long id;


}
