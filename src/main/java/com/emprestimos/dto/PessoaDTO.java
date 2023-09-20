package com.emprestimos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.emprestimos.indicator.TipoIdentificador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaDTO {

	private Long id;

	@NotBlank(message = "Atributo nome é obrigatório")
	private String nome;

	@NotNull(message = "Atributo dataNascimento é obrigatório")
	private LocalDate dataNascimento;

	@NotBlank(message = "Atributo identificador é obrigatório")
	private String identificador;

	@NotNull(message = "Atributo tipoIdentificador é obrigatório")
	private TipoIdentificador tipoIdentificador;

	private BigDecimal valorMinimoMensal;
	private BigDecimal valorMaximoEmprestimo;
}
