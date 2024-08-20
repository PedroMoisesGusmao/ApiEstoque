package com.example.bancopicpay.services;

import com.example.bancopicpay.models.Cliente;
import com.example.bancopicpay.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {
    public ClienteRepository clienteRepository;
    public ClienteService (ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    public Cliente buscarPorCpf(String cpf) {
        Optional<Cliente> cliente = clienteRepository.findById(cpf);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            return null;
        }
    }
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeLikeIgnoreCase(nome);
    }
    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmailLikeIgnoreCase(email);
    }
    public List<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefoneLike(telefone);
    }
    public Cliente cadastrarCliente(Cliente clienteRequisicao) {
        Optional<Cliente> clienteBanco = clienteRepository.findById(clienteRequisicao.getCpf());
        if (clienteBanco.isEmpty()) {
            return clienteRepository.save(clienteRequisicao);
        } else {
            return null;
        }
    }
    public Cliente atualizarCliente(Cliente clienteRequisicao) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteRequisicao.getCpf());
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNome(clienteRequisicao.getNome());
            cliente.setEmail(clienteRequisicao.getEmail());
            cliente.setTelefone(clienteRequisicao.getTelefone());
            clienteRepository.save(cliente);
            return cliente;
        } else {
            return null;
        }
    }

    public Cliente atualizarClienteParcialmente(String cpf, Map<String, Object> updates) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cpf);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            if (updates.containsKey("nome")) {
                cliente.setNome((String) updates.get("nome"));
            }
            if (updates.containsKey("email")) {
                cliente.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("telefone")) {
                cliente.setTelefone((String) updates.get("telefone"));
            }
            clienteRepository.save(cliente);
            return cliente;
        } else {
            return null;
        }
    }

    public Cliente removerCliente(String cpf) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cpf);
        if (clienteOptional.isPresent()) {
            clienteRepository.delete(clienteOptional.get());
            return clienteOptional.get();
        }
        return null;
    }
}
