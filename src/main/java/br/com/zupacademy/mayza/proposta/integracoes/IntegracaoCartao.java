package br.com.zupacademy.mayza.proposta.integracoes;

import br.com.zupacademy.mayza.proposta.cartoes.associa_cartao.CartaoPropostaRequest;
import br.com.zupacademy.mayza.proposta.cartoes.associa_cartao.CartaoPropostaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${api.cartoes.url}", name = "cartoes")
public interface IntegracaoCartao {

    @GetMapping
    public CartaoPropostaResponse buscaNumeroCartao(CartaoPropostaRequest request);
}
