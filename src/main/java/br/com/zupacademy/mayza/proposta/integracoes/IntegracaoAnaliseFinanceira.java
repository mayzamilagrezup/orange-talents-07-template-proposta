package br.com.zupacademy.mayza.proposta.integracoes;

import br.com.zupacademy.mayza.proposta.propostas.SolicitacaoAnaliseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${api.analise.url}", name = "analise")
public interface IntegracaoAnaliseFinanceira {

    @PostMapping
    public String solicitaAnalise(SolicitacaoAnaliseRequest request);
}
