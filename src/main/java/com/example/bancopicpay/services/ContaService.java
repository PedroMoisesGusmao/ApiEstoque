package com.example.bancopicpay.services;

import com.example.bancopicpay.models.Conta;
import com.example.bancopicpay.repositories.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContaService {
    ContaRepository contaRepository;
    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public List<Conta> buscarTodos() {
        return contaRepository.findAll();
    }

    public Conta buscarPorId(String numeroConta) {
        Optional<Conta> contaOptional = contaRepository.findById(numeroConta);
        if (contaOptional.isPresent()) {
            return contaOptional.get();
        }
        return null;
    }

    public Map<String, String> transferir(String origem, String destino, double valor) {
        if (valor <= 0) {
            Map<String, String> erro = new HashMap<>();
            erro.put("status", "400");
            erro.put("mensagem", "Valor não pode ser negativo.");
            return erro;
        }
        Optional<Conta> contaOrigemOptional = contaRepository.findById(origem);
        Optional<Conta> contaDestinoOptional = contaRepository.findById(destino);
        Map<String, String> resposta = new HashMap<>();
        if (contaOrigemOptional.isPresent()) {
            if (contaDestinoOptional.isPresent()) {
                Conta contaOrigem = contaOrigemOptional.get();
                Conta contaDestino = contaDestinoOptional.get();
                if (contaOrigem.getSaldo() - valor > (contaOrigem.getLimiteEspecial()) * (-1)) {
                    contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
                    contaDestino.setSaldo(contaDestino.getSaldo() + valor);
                    contaRepository.save(contaOrigem);
                    contaRepository.save(contaDestino);
                    resposta.put("conta_origem", contaOrigem.toString());
                    resposta.put("conta_destino", contaDestino.toString());
                    return resposta;
                } else {
                    resposta.put("status", "400");
                    resposta.put("mensagem", "Valor ultrapassa limite_especial.");
                    return resposta;
                }
            }
            resposta.put("status", "400");
            resposta.put("mensagem", "Não existe nenhuma conta com o cpf de destino passado.");
            return resposta;
        }
        resposta.put("status", "400");
        resposta.put("mensagem", "Não existe nenhuma conta com o cpf de origem passado.");
        return resposta;
    }
}
