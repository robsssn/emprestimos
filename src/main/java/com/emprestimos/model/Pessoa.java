package com.emprestimos.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "identificador", nullable = false, length = 50, unique = true)
	private String identificador;

	@Column(name = "tipo_identificador", nullable = false, length = 50)
	private String tipoIdentificador;

	@Column(name = "valor_min_emprestimo", nullable = false, precision = 18, scale = 4)
	private BigDecimal valorMinimoMensal;

	@Column(name = "valor_max_emprestimo", nullable = false, precision = 18, scale = 4)
	private BigDecimal valorMaximoEmprestimo;
}
