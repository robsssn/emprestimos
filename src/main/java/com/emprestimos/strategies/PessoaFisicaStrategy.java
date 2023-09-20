package com.emprestimos.strategies;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emprestimos.exception.CustomException;
import com.emprestimos.interfaces.TipoIndicadorStrategy;
import com.emprestimos.util.ValidadorCPF;

@Component
public class PessoaFisicaStrategy implements TipoIndicadorStrategy {

	@Override
	public BigDecimal getValorMinimoMensalParcelas() {
		return new BigDecimal(300);
	}

	@Override
	public BigDecimal getValorMaximoEmprestimo() {
		return new BigDecimal(10000);
	}

	@Override
	public void validarIdentificador(String identificador) {
		final String EXCEPTION_TEXT = "Identificador CPF Inválido.";
		if (identificador.length() != 11) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		if (!ValidadorCPF.isCPF(identificador)) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
