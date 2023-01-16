package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.dto.NotaSaidaDTO;
import br.gov.al.empresa.produto.dto.form.NotaSaidaFormDTO;
import br.gov.al.empresa.produto.services.NotaSaidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/saidas")
public class NotaSaidaResource {

    private final NotaSaidaService service;

    @Autowired
    public NotaSaidaResource(NotaSaidaService service) {
        this.service = service;
    }

    @Operation(description = "Obter todas as notas de saídas")
    @GetMapping
    public ResponseEntity<Collection<NotaSaidaDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(description = "Buscar nota de saída pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotaSaidaDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(description = "Salvar uma nota de saída")
    @PostMapping
    public ResponseEntity<NotaSaidaDTO> save(@RequestBody NotaSaidaFormDTO notaSaidaFormDTO) {
        return new ResponseEntity<>(service.save(notaSaidaFormDTO), HttpStatus.CREATED);
    }

    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Remover uma nota de saída pelo ID", tags = {"NotaSaida"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Nota de Saída Id")},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Não é possível excluir, existem associações."),
                    @ApiResponse(responseCode = "404", description = "Nota de Saída não encontrada")})
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}
