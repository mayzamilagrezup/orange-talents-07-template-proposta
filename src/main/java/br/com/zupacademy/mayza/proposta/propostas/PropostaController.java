package br.com.zupacademy.mayza.proposta.propostas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private SolicitaAnaliseProposta solicitaAnaliseProposta;
    private final Logger log = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository propostaRepository, SolicitaAnaliseProposta solicitaAnaliseProposta) {
        this.propostaRepository = propostaRepository;
        this.solicitaAnaliseProposta = solicitaAnaliseProposta;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder) {

        String documento = request.getDocumento();
        if (propostaRepository.findByDocumento(documento).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para o documento informado");
        }

        Proposta novaProposta = request.toProposta();
        propostaRepository.save(novaProposta);
        log.info("A proposta de documento: {} foi criada com sucesso!", novaProposta.getDocumento());

        StatusProposta retornoAnalise =  solicitaAnaliseProposta.analisa(novaProposta);
        novaProposta.atualizaStatusProposta(retornoAnalise);
        log.info("Status da proposta {}", novaProposta.getStatus());

        URI uri = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDaPropostaResponse> consultar(@PathVariable Long id) {

        Optional<Proposta> proposta = propostaRepository.findById(id);
        if (proposta.isEmpty()) {
            log.error("A Proposta de id {} não foi encontrada", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalhesDaPropostaResponse(proposta.get()));
    }
}
