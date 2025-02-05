package br.com.zupacademy.mayza.proposta.integracoes;

import br.com.zupacademy.mayza.proposta.aviso_viagem.AvisoDeViagemRequest;
import br.com.zupacademy.mayza.proposta.carteiras.CarteiraRequest;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoPropostaRequest;
import br.com.zupacademy.mayza.proposta.cartoes.CartaoPropostaResponse;
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

    @PostMapping("/{id}/avisos")
    public String notificaViagem(@PathVariable("id") String numeroCartao, AvisoDeViagemRequest request);

    @PostMapping("/{id}/carteiras")
    public String associaCarteira(@PathVariable("id") String numeroCartao, CarteiraRequest request);


}
