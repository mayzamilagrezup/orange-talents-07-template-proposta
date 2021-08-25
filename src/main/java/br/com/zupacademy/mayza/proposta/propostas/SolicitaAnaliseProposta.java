package br.com.zupacademy.mayza.proposta.propostas;

import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoAnaliseFinanceira;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SolicitaAnaliseProposta {

    private IntegracaoAnaliseFinanceira integracao;
    private final Logger log = LoggerFactory.getLogger(SolicitaAnaliseProposta.class);

    public SolicitaAnaliseProposta(IntegracaoAnaliseFinanceira integracao) {
        this.integracao = integracao;
    }

    public StatusProposta analisa(Proposta proposta) {

        try {
            SolicitacaoAnaliseRequest analiseSolicitada = new SolicitacaoAnaliseRequest(proposta);
            String resposta = integracao.solicitaAnalise(analiseSolicitada);
            log.info("A proposta de documento {} foi analisada com sucesso. Resposta {}", proposta.getDocumento(), resposta);

            return StatusProposta.ELEGIVEL;

        } catch (FeignException.UnprocessableEntity e) {
            log.info("A proposta de documento {} foi analisada com sucesso.", proposta.getDocumento());

            return StatusProposta.NAO_ELEGIVEL;

        } catch (Exception ex){
            log.error("Ocorreu um erro ao chamar a api de solicitacao financeira. Mensagem: {}", ex.getMessage(), ex);
            return StatusProposta.PENDENTE;
        }
    }
}
