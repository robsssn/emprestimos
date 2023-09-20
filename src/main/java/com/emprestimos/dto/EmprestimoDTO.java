package com.emprestimos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmprestimoDTO {

	private Long id;

	@NotNull(message = "Atributo valorEmprestimo é obrigatótio")
	private BigDecimal valorEmprestimo;

	@NotNull(message = "Atributo numeroParcelas é obrigatótio")
	private int numeroParcelas;
	private String statusPagamento;
	private LocalDateTime dataCriacao;
	private String nomePessoa;
	private String identificadorPessoa;
	private String tipoIdentificadorPessoa;
}
