package br.com.zupacademy.mayza.proposta.propostas.analise_financeira;

import br.com.zupacademy.mayza.proposta.propostas.Proposta;

public class SolicitacaoAnaliseRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = String.valueOf(proposta.getId());
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
