package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.dto.NotaEntradaDTO;
import br.gov.al.empresa.produto.dto.form.NotaEntradaFormDTO;
import br.gov.al.empresa.produto.services.NotaEntradaService;
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
@RequestMapping(value = "/entradas")
public class NotaEntradaResource {

    private final NotaEntradaService service;

    @Autowired
    public NotaEntradaResource(NotaEntradaService service) {
        this.service = service;
    }

    @Operation(description = "Obter todas as notas de entrada")
    @GetMapping
    public ResponseEntity<Collection<NotaEntradaDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(description = "Buscar nota de entrada pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotaEntradaDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(description = "Salvar uma nota de entrada")
    @PostMapping
    public ResponseEntity<NotaEntradaDTO> save(@RequestBody NotaEntradaFormDTO notaEntradaFormDTO) {
        return new ResponseEntity<>(service.save(notaEntradaFormDTO), HttpStatus.CREATED);
    }

    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Remover uma nota de entrada pelo ID", tags = {"NotaEntrada"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Nota de Saída Id")},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Não é possível excluir, existem associações."),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}
