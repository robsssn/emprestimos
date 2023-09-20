package com.emprestimos.interfaces;

import java.math.BigDecimal;

public interface TipoIndicadorStrategy {

	BigDecimal getValorMinimoMensalParcelas();
	BigDecimal getValorMaximoEmprestimo();
	void validarIdentificador(String identificador);
}
