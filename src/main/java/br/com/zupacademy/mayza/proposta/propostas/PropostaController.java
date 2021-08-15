package br.com.zupacademy.mayza.proposta.propostas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder builder) {

        Proposta novaProposta = request.toProposta();
        propostaRepository.save(novaProposta);

        URI uri = builder.path("/propostas/{id}").build(novaProposta.getId());

        return ResponseEntity.created(uri).build();
    }
}
