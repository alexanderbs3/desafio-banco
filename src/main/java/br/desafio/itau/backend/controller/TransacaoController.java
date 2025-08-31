package br.desafio.itau.backend.controller;

import br.desafio.itau.backend.model.Estatisticas;
import br.desafio.itau.backend.model.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/transacoes") // Path base
@RequiredArgsConstructor
public class TransacaoController {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    // CREATE
    @PostMapping
    public ResponseEntity<Void> criarTransacao(@RequestBody Transacao transacao) {
        if (!adicionarTransacao(transacao)) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        return ResponseEntity.status(201).build(); // 201 Created
    }

    private boolean adicionarTransacao(Transacao transacao) {
        if (transacao.getValor() <= 0 || transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            return false;
        }
        transacoes.add(transacao);
        return true;
    }

    // READ
    @GetMapping
    public ResponseEntity<List<Estatisticas>> obterEstatisticas() {
//        OffsetDateTime agora = OffsetDateTime.now();
//        List<Transacao> ultimos60Segundos = transacoes.stream()
//                .filter(t -> t.getDataHora().isAfter(agora.minusSeconds(60)))
//                .collect(Collectors.toList());

        DoubleSummaryStatistics status60s = transacoes.stream().filter(t -> t.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(60)))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();
        Estatisticas estatisticas = new Estatisticas(
                status60s.getCount(),
                status60s.getSum(),
                status60s.getAverage(),
                status60s.getCount() == 0 ? 0 : status60s.getMin(),
                status60s.getCount() == 0 ? 0 : status60s.getMax()
        );
        return ResponseEntity.ok(List.of(estatisticas)); // 200 OK
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarTransacao(
            @PathVariable Long id,
            @RequestBody Transacao transacao) {

        if (transacao.getValor() <= 0 || transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            return ResponseEntity.badRequest().build(); // 400
        }

        for (int i = 0; i < transacoes.size(); i++) {
            if (transacoes.get(i).getId().equals(id)) {
                transacao.setId(id);
                transacoes.set(i, transacao);
                return ResponseEntity.noContent().build(); // 204
            }
        }

        return ResponseEntity.notFound().build(); // 404
    }

    // DELETE ALL
    @DeleteMapping
    public ResponseEntity<Void> deletarTransacoes() {
        transacoes.clear();
        return ResponseEntity.noContent().build(); // 204
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        for (Transacao t : transacoes) {
            if (t.getId().equals(id)) {
                transacoes.remove(t);
                return ResponseEntity.noContent().build(); // 204
            }
        }
        return ResponseEntity.notFound().build(); // 404
    }
}
