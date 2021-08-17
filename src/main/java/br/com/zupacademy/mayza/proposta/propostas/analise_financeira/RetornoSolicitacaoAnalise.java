package br.com.zupacademy.mayza.proposta.propostas.analise_financeira;

import br.com.zupacademy.mayza.proposta.propostas.StatusProposta;

public enum RetornoSolicitacaoAnalise {

    COM_RESTRICAO(StatusProposta.NAO_ELEGIVEL),
    SEM_RESTRICAO(StatusProposta.ELEGIVEL);

    private StatusProposta statusProposta;

    private RetornoSolicitacaoAnalise(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }
}
