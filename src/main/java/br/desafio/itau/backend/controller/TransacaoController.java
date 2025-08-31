package br.desafio.itau.backend.controller;

import br.desafio.itau.backend.model.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping
@RequiredArgsConstructor


public class TransacaoController {

    private final List<Transacao> transacaos = new CopyOnWriteArrayList<>();

    @PostMapping("/transacoes")
    public ResponseEntity<Void> criarTransacao(@RequestBody Transacao transacao) {
        boolean adicionada = adicionarTransacao(transacao);
        if (adicionada == true) {
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    private boolean adicionarTransacao(Transacao transacao) {
        if (transacao.getValor() <= 0 || transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            return false;
        }
        transacaos.add(transacao);
        return true;
    }

}


