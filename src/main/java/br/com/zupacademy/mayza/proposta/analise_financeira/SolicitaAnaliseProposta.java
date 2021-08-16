package br.com.zupacademy.mayza.proposta.analise_financeira;

import br.com.zupacademy.mayza.proposta.integracoes.IntegracaoAnaliseFinanceira;
import br.com.zupacademy.mayza.proposta.propostas.Proposta;
import br.com.zupacademy.mayza.proposta.propostas.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitaAnaliseProposta {

    @Autowired
    private IntegracaoAnaliseFinanceira integracao;
    private final Logger log = LoggerFactory.getLogger(SolicitaAnaliseProposta.class);

    public StatusProposta analisa(Proposta proposta) {

        try {
            SolicitacaoAnaliseRequest analiseSolicitada = new SolicitacaoAnaliseRequest(proposta);
            String resulatado = integracao.solicitaAnalise(analiseSolicitada);
            log.info("A proposta de documento {} foi analisada com sucesso.", proposta.getDocumento());

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
