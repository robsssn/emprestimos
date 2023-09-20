package com.emprestimos.strategies;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emprestimos.exception.CustomException;
import com.emprestimos.interfaces.TipoIndicadorStrategy;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EstudanteUniversitarioStrategy implements TipoIndicadorStrategy {

	@Override
	public BigDecimal getValorMinimoMensalParcelas() {
		return new BigDecimal(100);
	}

	@Override
	public BigDecimal getValorMaximoEmprestimo() {
		return new BigDecimal(10000);
	}

	@Override
	public void validarIdentificador(String identificador) {
		final String EXCEPTION_TEXT = "Identificador Estudante Universitário Inválido.";

		if (identificador.length() != 8) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		String primeiroDigito = identificador.substring(0, 1);
		String ultimoDigito = identificador.substring(7, 8);

		try {
			int primeiroDigitoInt = Integer.parseInt(primeiroDigito);
			int ultimoDigitoInt = Integer.parseInt(ultimoDigito);
			int digitoCalculado = primeiroDigitoInt + ultimoDigitoInt;
			if (digitoCalculado != 9) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		}  catch (Exception e) {
			throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
