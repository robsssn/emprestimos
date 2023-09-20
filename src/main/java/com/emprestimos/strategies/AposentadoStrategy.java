package com.emprestimos.strategies;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emprestimos.exception.CustomException;
import com.emprestimos.interfaces.TipoIndicadorStrategy;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AposentadoStrategy implements TipoIndicadorStrategy {

	@Override
	public BigDecimal getValorMinimoMensalParcelas() {
		return new BigDecimal(400);
	}

	@Override
	public BigDecimal getValorMaximoEmprestimo() {
		return new BigDecimal(25000);
	}

	@Override
	public void validarIdentificador(String identificador) {
		final String EXCEPTION_TEXT = "Identificador Aposentado Inválido.";
		if (identificador.length() != 10) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
		String ultimoDigito = identificador.substring(9, 10);
		String restoDigitos = identificador.substring(0, 9);
		log.info("[validarIdentificador] restoDigitos: {}", restoDigitos);
		log.info("[validarIdentificador] ultimoDigito: {}", ultimoDigito);

		if (restoDigitos.contains(ultimoDigito)) throw new CustomException(EXCEPTION_TEXT, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
