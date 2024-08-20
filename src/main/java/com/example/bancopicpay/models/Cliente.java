package com.example.bancopicpay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Cliente {
    public Cliente(){};

    public Cliente(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    @Id
    @NotNull(message = "O cpf não pode ser nulo.")
    @NotBlank(message = "O campo cpf não pode ser vazio.")
    @Size(min = 11, max = 11, message = "CPF inválido.")
    //@CPF
    private String cpf;
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 2, message = "O nome não pode ter menos que 2 letras.")
    private String nome;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Size(min = 11, message = "O e-mail não pode ter menos de 11 letras.")
    @Email
    private String email;

    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone não pode ser vazio.")
    @Size(min = 11, message = "Tamanho do telefone inválido")
    private String telefone;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
