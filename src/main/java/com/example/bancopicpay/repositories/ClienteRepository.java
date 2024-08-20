package com.example.bancopicpay.repositories;

import com.example.bancopicpay.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findByNomeLikeIgnoreCase(String nome);
    List<Cliente> findByEmailLikeIgnoreCase(String email);
    List<Cliente> findByTelefoneLike(String telefone);
}
