package com.emprestimos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emprestimos.dto.EmprestimoDTO;
import com.emprestimos.exception.CustomException;
import com.emprestimos.indicator.StatusPagamentoIndicador;
import com.emprestimos.model.Emprestimo;
import com.emprestimos.model.Pessoa;
import com.emprestimos.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	private final int NUMERO_MAX_PARCELAS_PERMITIDAS = 24;
	private final int NUMERO_MIN_PARCELAS_PERMITIDAS = 1;

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	@Autowired
	private PessoaService pessoaService;

	public void realizarEmprestimo(String identificador, EmprestimoDTO emprestimoDTO) throws Exception {
		final Pessoa pessoa = pessoaService.findByIdentificador(identificador);
		validarQuantidadeMinimaParcelas(emprestimoDTO.getNumeroParcelas());
		validarQuantidadeMaximaParcelas(emprestimoDTO.getNumeroParcelas());
		validarValorMaximoEmprestimoTipoPessoa(emprestimoDTO.getValorEmprestimo(), pessoa.getValorMaximoEmprestimo());
		validarValorMinimoParcelasTipoPessoa(emprestimoDTO.getValorEmprestimo(), pessoa.getValorMinimoMensal(), emprestimoDTO.getNumeroParcelas());
		Emprestimo emprestimo = buildEmprestimo(pessoa, emprestimoDTO);
		emprestimoRepository.save(emprestimo);
	}

	private Emprestimo buildEmprestimo(Pessoa pessoa, EmprestimoDTO emprestimoDTO) {
		return Emprestimo.builder()
				.pessoa(pessoa)
				.dataCriacao(LocalDateTime.now())
				.numeroParcelas(emprestimoDTO.getNumeroParcelas())
				.valorEmprestimo(emprestimoDTO.getValorEmprestimo())
				.statusPagamento(StatusPagamentoIndicador.PENDENTE.name())
				.build();
	}

	private void validarValorMaximoEmprestimoTipoPessoa(BigDecimal valorSolicitado, BigDecimal valorMaximoPermitido) {
		if (valorSolicitado.compareTo(valorMaximoPermitido) == 1) throw new CustomException("Valor de emprestimo solicitado maior que o permitido.", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private void validarQuantidadeMinimaParcelas(int parecelasSolicitadas) {
		if (parecelasSolicitadas < NUMERO_MIN_PARCELAS_PERMITIDAS) throw new CustomException("Quantidade de parecelas menor que o permitido.", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private void validarQuantidadeMaximaParcelas(int parecelasSolicitadas) {
		if (parecelasSolicitadas > NUMERO_MAX_PARCELAS_PERMITIDAS) throw new CustomException("Quantidade de parcelas maior que o permitido.", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private void validarValorMinimoParcelasTipoPessoa(BigDecimal valorEmprestimo, BigDecimal valorParcelaMinimaPessoa, int quantidadeParcelas) throws Exception {
		BigDecimal valorMinimoParcelasCalculado = valorEmprestimo.setScale(4).divide(BigDecimal.valueOf(quantidadeParcelas), RoundingMode.HALF_UP);
		if (valorMinimoParcelasCalculado.compareTo(valorParcelaMinimaPessoa) == -1) throw new CustomException("Valor das parcelas menor do que o permitido.", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public List<EmprestimoDTO> listEmprestimosByIdentificadorPessoa(String identificadorPessoa) {
		return emprestimoRepository.findEmprestimosByIdentificadorPessoa(identificadorPessoa).stream()
				.map(emprestimo -> buildEmprestimoDTO(emprestimo))
				.collect(Collectors.toList());
	}

	private EmprestimoDTO buildEmprestimoDTO(Emprestimo emprestimo) {
		return EmprestimoDTO.builder()
				.id(emprestimo.getId())
				.valorEmprestimo(emprestimo.getValorEmprestimo())
				.numeroParcelas(emprestimo.getNumeroParcelas())
				.statusPagamento(emprestimo.getStatusPagamento())
				.nomePessoa(emprestimo.getPessoa().getNome())
				.identificadorPessoa(emprestimo.getPessoa().getIdentificador())
				.tipoIdentificadorPessoa(emprestimo.getPessoa().getTipoIdentificador())
				.dataCriacao(emprestimo.getDataCriacao())
				.build();
	}

	public void pagarEmprestimo(Long idEmprestimo) {
		Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo).orElseThrow(() -> new CustomException("Não encontrou emprestimo.", HttpStatus.NO_CONTENT));
		if (emprestimo.getStatusPagamento().equals(StatusPagamentoIndicador.PAGO.name())) throw new CustomException("Este emprestimo já foi pago.", HttpStatus.UNPROCESSABLE_ENTITY);
		emprestimo.setStatusPagamento(StatusPagamentoIndicador.PAGO.name());
		emprestimoRepository.save(emprestimo);
	}

	public List<Emprestimo> findEmprestimoByPessoaId(Long pessoaId) {
		return emprestimoRepository.findEmprestimoByPessoaId(pessoaId);
	}
}
