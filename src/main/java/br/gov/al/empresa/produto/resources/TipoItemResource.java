package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.services.TipoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(value = "/tipo-itens")
public class TipoItemResource {

    private final TipoItemService service;

    @Autowired
    public TipoItemResource(TipoItemService service) {
        this.service = service;
    }

    @Operation(description = "Obter todos os tipos de itens")
    @GetMapping
    public ResponseEntity<Collection<TipoItemDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(description = "Buscar tipo de item pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<TipoItemDTO> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(description = "Salvar um tipo de item")
    @PostMapping
    public ResponseEntity<TipoItemDTO> save(@RequestBody @Valid TipoItemFormDTO tipoItemFormDTO) {
        return new ResponseEntity<>(service.save(tipoItemFormDTO), HttpStatus.CREATED);
    }

    @Operation(description = "Alterar dados de um tipo de item")
    @PutMapping("/{id}")
    public ResponseEntity<TipoItemDTO> update(@PathVariable("id") Long id, @RequestBody @Valid TipoItemFormDTO tipoItemFormDTO) {
        return new ResponseEntity<>(service.update(id, tipoItemFormDTO), HttpStatus.OK);
    }

    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Remover um tipo de item pelo ID", tags = {"Item"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Item Id")},
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
