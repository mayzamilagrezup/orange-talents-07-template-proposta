package br.com.zupacademy.mayza.proposta.carteiras;

import br.com.zupacademy.mayza.proposta.cartoes.Cartao;
import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoCartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AssociadorDeCarteiras {

    private IntegracaoCartao integracaoCartao;
    private final Logger log = LoggerFactory.getLogger(AssociadorDeCarteiras.class);

    public AssociadorDeCarteiras(IntegracaoCartao integracaoCartao) {
        this.integracaoCartao = integracaoCartao;
    }

    public void associaCarteira(Cartao cartao,CarteiraRequest request) {
        try {
            String resposta = integracaoCartao.associaCarteira(cartao.getNumeroDoCartao(), request);
            log.info("Associação da carteira {} para o cartão {}, resposta {}", request.getCarteira(), cartao.getNumeroDoCartao(), resposta);

        } catch (FeignException e) {
            log.error("Não foi possível associar a carteira {} ao cartão {}. Motivo: {}", request.getCarteira(), cartao.getNumeroDoCartao(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Ocorreu um erro ao associar carteira ao cartão. Tente novamente.");
        }
    }
}
