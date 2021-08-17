package br.com.zupacademy.mayza.proposta.cartoes.associa_cartao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoPropostaResponse {

    @JsonProperty("id")
    private String numeroDoCartao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoPropostaResponse(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }
}
