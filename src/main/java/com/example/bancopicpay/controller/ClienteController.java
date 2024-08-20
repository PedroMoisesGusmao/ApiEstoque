package com.example.bancopicpay.controller;

import com.example.bancopicpay.models.Cliente;
import com.example.bancopicpay.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/banco/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Realiza a consulta de um determinado cliente através de diferentes atributos", method = "GET")
    @ApiResponse(responseCode = "200", description = "A consulta foi realizada com sucesso")
    @GetMapping("busca")
    public ResponseEntity<?> buscar(@RequestParam(value = "cpf", required = false) String cpf,
                                    @RequestParam(value = "nome", required = false) String nome,
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "telefone", required = false) String telefone) {
        if (cpf != null) {
            return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
        } else if (nome != null) {
            return ResponseEntity.ok(clienteService.buscarPorNome(nome));
        } else if (email != null) {
            return ResponseEntity.ok(clienteService.buscarPorEmail(email));
        } else if (telefone != null) {
            return ResponseEntity.ok(clienteService.buscarPorTelefone(telefone));
        }
        return  ResponseEntity.ok(clienteService.buscarTodos());
    }

    @Operation(summary = "Realiza a operação de inserir um cliente no banco de dados", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF já está na base de dados")
    })

    @PostMapping("inserir")
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid Cliente cliente) {
        Cliente retornoBanco = clienteService.cadastrarCliente(cliente);
        if (retornoBanco != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(retornoBanco);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado, tente novamente com outro usuário");
        }
    }

    @Operation(summary = "Realiza a operação de atualizar um cliente dentro da base de dados", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Atualização foi realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "CPF não encontrado")
    })

    @PutMapping("atualizar")
    public ResponseEntity<?> atualizarCliente(@RequestBody @Valid Cliente cliente) {
        Cliente resposta = clienteService.atualizarCliente(cliente);
        if (resposta != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(resposta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum dos usuários tem esse CPF.");
        }
    }

    @Operation(summary = "Realiza a alteração de um cliente com base em um atributo específico do mesmo", method = "PATCH")
    @ApiResponse(responseCode = "200", description = "O atributo selecionado do cliente pode ser alterado com sucesso")

    @PatchMapping("atualizar/{cpf}")
    public ResponseEntity<?> atualizarCliente(@PathVariable String cpf, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(clienteService.atualizarClienteParcialmente(cpf, updates));
    }

    @Operation(summary = "Realiza a remoção de um cliente na base com base no CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A remoção foi concluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF inserido não está no banco de dados")
    })

    @DeleteMapping("remover/{cpf}")
    public ResponseEntity<?> removerCliente(@PathVariable String cpf) {
        if (cpf.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
        }
        Cliente cliente = clienteService.removerCliente(cpf);
        if (cliente != null) {
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não foi encontrado");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> verificarCliente(BindingResult result) {
        Map<String, Object> erros = new HashMap<>();
        for (FieldError error: result.getFieldErrors()) {
            erros.put(error.getField(), error.getDefaultMessage());
        }
        return erros;
    }
}
