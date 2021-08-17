package br.com.zupacademy.mayza.proposta.propostas;

public class DetalhesDaPropostaResponse {

    private Long id;
    private StatusProposta status;

    public DetalhesDaPropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.status = proposta.getStatus();
    }

    public Long getId() {
        return id;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
