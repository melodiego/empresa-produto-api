package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.dto.ItemDTO;
import br.gov.al.empresa.produto.dto.form.ItemFormDTO;
import br.gov.al.empresa.produto.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/itens")
public class ItemResource {

    private final ItemService service;

    @Autowired
    public ItemResource(ItemService service) {
        this.service = service;
    }

    @Operation(description = "Obter todos os produtos")
    @GetMapping
    public ResponseEntity<Collection<ItemDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(description = "Buscar produto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(description = "Salvar um produto")
    @PostMapping
    public ResponseEntity<ItemDTO> save(@RequestBody ItemFormDTO itemFormDTO) {
        return new ResponseEntity<>(service.save(itemFormDTO), HttpStatus.CREATED);
    }

    @Operation(description = "Alterar dados de um item")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable("id") UUID id, @RequestBody ItemFormDTO itemFormDTO) {
        return new ResponseEntity<>(service.update(id, itemFormDTO), HttpStatus.OK);
    }

    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Remover um item pelo ID", tags = {"Item"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Item Id")},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Não é possível excluir, existem associações."),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") UUID id) {
        service.delete(id);
    }

    @Operation(description = "Listagem passando paramêtro da página")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ItemDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                                  @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                  @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<ItemDTO> findPage = service.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(findPage);
    }
}
