package com.example.bancopicpay.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Random;

@Entity
public class Conta {
    @Id
    @Column(name = "numero_conta")
    @NotNull(message = "Atributo numero_conta não pode ser nulo.")
    @NotBlank(message = "Atributo numero_conta não pode ser vazio.")
    private String numeroConta;
    @NotNull(message = "Atributo saldo não pode ser nulo.")
    private double saldo;
    @Column(name = "limite_especial")
    @Min(value = 0, message = "O limite especial não pode ser menor que 0.")
    @NotNull(message = "Atributo limite_especial não pode ser nulo.")
    private double limiteEspecial;
    @Column(name = "cliente_cpf")
    @NotNull(message = "Atributo cliente_cpf não pode ser nulo. ")
    @NotBlank(message = "Atributo cliente_cpf não pode ser vazio.")
    private String clienteCpf;

    public Conta() {}

    public Conta(String numeroConta,  double saldo, double limiteEspecial, String clienteCpf) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.limiteEspecial = limiteEspecial;
        this.clienteCpf = clienteCpf;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimiteEspecial() {
        return limiteEspecial;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setLimiteEspecial(double limiteEspecial) {
        this.limiteEspecial = limiteEspecial;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }
}
