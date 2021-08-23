package br.com.zupacademy.mayza.proposta.cartoes;

import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoCartao;
import br.com.zupacademy.mayza.proposta.propostas.Proposta;
import br.com.zupacademy.mayza.proposta.propostas.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociadorDeCartoesProposta {

    private IntegracaoCartao integracaoCartao;
    private PropostaRepository propostaRepository;
    private final Logger log = LoggerFactory.getLogger(AssociadorDeCartoesProposta.class);

    public AssociadorDeCartoesProposta(IntegracaoCartao integracaoCartao, PropostaRepository propostaRepository) {
        this.integracaoCartao = integracaoCartao;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelayString = "${periodicidade.associa-cartao-proposta}")
    public void associa() {

        List<Proposta> propostasElegiveis = propostaRepository.buscaTodasPropostasElegiveisSemCartao();
        log.info("Total de propostas elegíveis sem cartão associado {}", propostasElegiveis.size());

        for (Proposta proposta : propostasElegiveis) {

            try {
                CartaoPropostaRequest request = new CartaoPropostaRequest(proposta);
                CartaoPropostaResponse response = integracaoCartao.buscaNumeroCartao(request);
                proposta.associaCartao(response);
                propostaRepository.save(proposta);

                log.info("Foi associado o cartão de número {} a proposta {}", response.getNumeroDoCartao(), proposta.getId());

            } catch (FeignException e) {
                log.error("Não foi possível associar cartão para a proposta {}. Mensagem {}. Causa {}", proposta.getId(), e.getMessage(), e.getCause());
            }
        }
    }



}
