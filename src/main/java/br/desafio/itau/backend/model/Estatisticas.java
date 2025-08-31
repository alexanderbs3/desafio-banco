package br.desafio.itau.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Estatisticas {


    private Long id;

    private long count;
    private double sum;
    private double avg;
    private double max;
    private double min;


    public Estatisticas(long count, double sum, double avg, double max, double min) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
    }
}
