package br.com.zupacademy.mayza.proposta.cartoes.associa_cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoPropostaResponse {

    @JsonProperty("id")
    private String numeroDoCartao;
    private LocalDateTime emitidoEm;
    private BigDecimal limite;

    public CartaoPropostaResponse(String numeroDoCartao, LocalDateTime emitidoEm, BigDecimal limite) {
        this.numeroDoCartao = numeroDoCartao;
        this.emitidoEm = emitidoEm;
        this.limite = limite;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }

}
