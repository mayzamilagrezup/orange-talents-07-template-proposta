package br.com.zupacademy.mayza.proposta.integracoes;

import br.com.zupacademy.mayza.proposta.cartoes.associa_cartao.CartaoPropostaRequest;
import br.com.zupacademy.mayza.proposta.cartoes.associa_cartao.CartaoPropostaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(url = "${api.cartoes.url}", name = "cartoes")
public interface IntegracaoCartao {

    @GetMapping
    public CartaoPropostaResponse buscaNumeroCartao(CartaoPropostaRequest request);

    @PostMapping("/{id}/bloqueios")
    public String notificaBloqueioCartao(@PathVariable("id") String numeroCartao,  Map<String, String> request);
}
