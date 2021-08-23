package br.com.zupacademy.mayza.proposta.bloqueio;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.cartoes.StatusCartao;
import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@Component
public class NotificadorDeBloqueioCartao {

    private IntegracaoCartao integracaoCartao;
    @PersistenceContext
    private EntityManager manager;
    private final Logger log = LoggerFactory.getLogger(NotificadorDeBloqueioCartao.class);

    public NotificadorDeBloqueioCartao(IntegracaoCartao integracaoCartao) {
        this.integracaoCartao = integracaoCartao;
    }

    public void notificaBloqueio(Cartao cartao, String ipCliente, String userAgent) {

        try {
            String resposta  = integracaoCartao.notificaBloqueioCartao(cartao.getNumeroDoCartao(), Map.of("sistemaResponsavel", "Propostas"));
            Bloqueio bloqueio = new Bloqueio(userAgent, ipCliente, cartao);
            cartao.setStatusCartao(StatusCartao.BLOQUEADO);
            manager.persist(bloqueio);
            log.info("Notificação de bloqueio para o cartão {} retornou {}.", cartao.getNumeroDoCartao(), resposta);


        } catch (FeignException e) {
            log.error("Não foi possivel notificar o bloqueio para o cartão {}. Motivo: {}", cartao.getNumeroDoCartao(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Ocorreu um erro ao notificar o sistema bancário. Tente novamente.");
        }

    }
}
