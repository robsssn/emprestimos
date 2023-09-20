package com.emprestimos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	@Column(name = "valor_emprestimo", nullable = false, precision = 18, scale = 4)
	private BigDecimal valorEmprestimo;

	@Column(name = "numero_parcelas", nullable = false)
	private int numeroParcelas;

	@Column(name = "status_pagamento", nullable = false, length = 50)
	private String statusPagamento;

	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
}
