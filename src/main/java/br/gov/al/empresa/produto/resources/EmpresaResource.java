package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.dto.EmpresaDTO;
import br.gov.al.empresa.produto.dto.ItemEstoqueDTO;
import br.gov.al.empresa.produto.dto.NotaEntradaDTO;
import br.gov.al.empresa.produto.dto.NotaSaidaDTO;
import br.gov.al.empresa.produto.dto.form.EmpresaFormDTO;
import br.gov.al.empresa.produto.services.EmpresaService;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {

    private final EmpresaService service;

    @Autowired
    public EmpresaResource(EmpresaService service) {
        this.service = service;
    }

    @Operation(description = "Obter todas as empresas")
    @GetMapping
    public ResponseEntity<Collection<EmpresaDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Operation(description = "Buscar empresa pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Operation(description = "Salvar uma empresa")
    @PostMapping
    public ResponseEntity<EmpresaDTO> save(@RequestBody EmpresaFormDTO empresaFormDTO) {
        return new ResponseEntity<>(service.save(empresaFormDTO), HttpStatus.CREATED);
    }

    @Operation(description = "Alterar dados de uma empresa")
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> update(@PathVariable("id") UUID id, @RequestBody EmpresaFormDTO empresaFormDTO) {
        return new ResponseEntity<>(service.update(id, empresaFormDTO), HttpStatus.OK);
    }

    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Remover uma empresa pelo ID", tags = {"Empresa"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Empresa Id")},
            responses = {
                    @ApiResponse(responseCode = "400", description = "Não é possível excluir, existem associações."),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") UUID id) {
        service.delete(id);
    }

    @Operation(description = "Listar todas as entradas de uma empresa")
    @GetMapping("/{id}/entradas")
    public ResponseEntity<Collection<NotaEntradaDTO>> getAllEntradasByEmpresa(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(service.findAllEntradasByEmpresaId(id), HttpStatus.OK);
    }

    @Operation(description = "Listar todas as saídas de uma empresa")
    @GetMapping("/{id}/saidas")
    public ResponseEntity<Collection<NotaSaidaDTO>> getAllSaidasByEmpresa(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(service.findAllSaidasByEmpresaId(id), HttpStatus.OK);
    }

    @Operation(description = "Listar todos os itens em estoque de uma empresa")
    @GetMapping("/{id}/itens-estoque")
    public ResponseEntity<Collection<ItemEstoqueDTO>> getAllItensEstoqueByEmpresa(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(service.findAllItensEstoqueByEmpresaId(id), HttpStatus.OK);
    }

    //@Operation(description = "Ativar um item em estoque de uma empresa")
    @PostMapping("/{empresaId}/itens-estoque/{id}/ativar")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ativarItemEstoque(@PathVariable(value = "empresaId") UUID empresaId, @PathVariable(value = "id") UUID id) {
        service.ativarItemEstoque(empresaId, id);
    }

    //@Operation(description = "Desativar um item em estoque de uma empresa")
    @PostMapping("/{empresaId}/itens-estoque/{id}/desativar")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desativarItemEstoque(@PathVariable(value = "empresaId") UUID empresaId, @PathVariable(value = "id") UUID id) {
        service.desativarItemEstoque(empresaId, id);
    }
}
