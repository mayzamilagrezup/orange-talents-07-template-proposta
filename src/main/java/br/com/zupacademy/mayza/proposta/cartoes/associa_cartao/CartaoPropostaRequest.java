package br.com.zupacademy.mayza.proposta.cartoes.associa_cartao;

import br.com.zupacademy.mayza.proposta.propostas.Proposta;

public class CartaoPropostaRequest {

    private String idProposta;

    public CartaoPropostaRequest(Proposta proposta) {
        this.idProposta = String.valueOf(proposta.getId());
    }

    public String getIdProposta() {
        return idProposta;
    }
}
