package br.com.zupacademy.mayza.proposta.cartoes;

import br.com.zupacademy.mayza.proposta.cartoes.associa_cartao.CartaoPropostaResponse;
import br.com.zupacademy.mayza.proposta.propostas.Proposta;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroDoCartao;
    private LocalDateTime emitidoEm;
    private BigDecimal limite;

    @OneToOne
    private Proposta proposta;

    public Cartao(CartaoPropostaResponse response, Proposta proposta) {
        this.numeroDoCartao = response.getNumeroDoCartao();
        this.emitidoEm = response.getEmitidoEm();
        this.limite = response.getLimite();
        this.proposta = proposta;
    }

    private Cartao() {
    }
}
