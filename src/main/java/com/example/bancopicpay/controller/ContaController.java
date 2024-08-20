package com.example.bancopicpay.controller;

import com.example.bancopicpay.models.Conta;
import com.example.bancopicpay.services.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banco/conta")
public class ContaController {
    ContaService contaService;
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @Operation(summary = "Consulta todas as contas registradas no banco")
    @ApiResponse(responseCode = "200", description = "Consulta ocorreu sem problemas.")

    @GetMapping("buscar")
    public ResponseEntity<List<Conta>> buscar() {
        return ResponseEntity.ok(contaService.buscarTodos());
    }

    @Operation(summary = "Realiza as operações de transferência de saldo entre contas", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Chave 'conta_origem' ou chave 'conta_destino' não encontrada.")
    })

    @PostMapping("transferir")
    public ResponseEntity<?> transferir(@RequestBody Map<String, Object> requisicao) {
        if (requisicao.containsKey("conta_origem") && requisicao.containsKey("conta_destino") && requisicao.containsKey("valor")) {
            String contaOrigem = (String) requisicao.get("conta_origem");
            String contaDestino = (String) requisicao.get("conta_destino");
            String valorString = (String) requisicao.get("valor");
            double valor = 0;

            try {
                valor = Double.parseDouble(valorString);
            } catch (ClassCastException cce) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valor deve ser um número.");
            }
            if (valor <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valor deve ser maior que 0.");
            } else {
                Map<String, String> resposta = contaService.transferir(contaOrigem, contaDestino, valor);
                if (resposta.containsKey("status")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
                }
                return ResponseEntity.ok(resposta);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falta de chave 'origem', 'destino', ou 'valor'.");
        }
    }

}
