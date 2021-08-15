package br.com.zupacademy.mayza.proposta.propostas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private final Logger log = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder) {

        String documento = request.getDocumento();
        if (propostaRepository.findByDocumento(documento).isPresent()) {
            log.info("Já existe uma proposta criada para o documento: " + documento);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe um proposta para o documento informado");
        }

        Proposta novaProposta = request.toProposta();
        propostaRepository.save(novaProposta);
        log.info("Proposta de documento: {} criada com sucesso!", novaProposta.getDocumento());

        URI uri = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(uri).build();
    }
}
