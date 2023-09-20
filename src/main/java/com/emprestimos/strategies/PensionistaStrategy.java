package com.emprestimos.strategies;

import java.math.BigDecimal;

import com.emprestimos.interfaces.TipoIndicadorStrategy;

public class PensionistaStrategy implements TipoIndicadorStrategy {

	@Override
	public BigDecimal getValorMinimoMensalParcelas() {
		return BigDecimal.valueOf(50);
	}

	@Override
	public BigDecimal getValorMaximoEmprestimo() {
		return BigDecimal.valueOf(280000);
	}

	@Override
	public void validarIdentificador(String identificador) {
		// tem que ter 7 digitos lança exceçao
	}
}
