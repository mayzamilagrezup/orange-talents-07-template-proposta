package br.com.zupacademy.mayza.proposta.aviso_viagem;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class NotificadorDeViagem {

    private IntegracaoCartao integracaoCartao;
    private final Logger log = LoggerFactory.getLogger(NotificadorDeViagem.class);

    public NotificadorDeViagem(IntegracaoCartao integracaoCartao) {
        this.integracaoCartao = integracaoCartao;
    }

    public void notificaViagem(Cartao cartao, AvisoDeViagemRequest request) {
        try {
            String resposta = integracaoCartao.notificaViagem(cartao.getNumeroDoCartao(), request);
            log.info("Aviso de viagem notificado para o cartão {}, resposta {}", cartao.getNumeroDoCartao(), resposta);

        } catch (FeignException e) {
            log.error("Não foi possivel notificar o aviso de viagem para o cartão {}. Motivo: {}", cartao.getNumeroDoCartao(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Ocorreu um erro ao notificar o sistema bancário. Tente novamente.");
        }
    }
}
