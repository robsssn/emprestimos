package com.emprestimos.strategies;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emprestimos.exception.CustomException;
import com.emprestimos.interfaces.TipoIndicadorStrategy;
import com.emprestimos.util.ValidadorCNPJ;

@Component
public class PessoaJuridicaStrategy implements TipoIndicadorStrategy {

	@Override
	public BigDecimal getValorMinimoMensalParcelas() {
		return new BigDecimal(1000);
	}

	@Override
	public BigDecimal getValorMaximoEmprestimo() {
		return new BigDecimal(100000);
	}

	@Override
	public void validarIdentificador(String identificador) {
		final String EXCEPTION_TEXT = "Identificador CNPJ Inválido.";
		if (identificador.length() != 14) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		if (!ValidadorCNPJ.isCNPJ(identificador)) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);;
	}
}
