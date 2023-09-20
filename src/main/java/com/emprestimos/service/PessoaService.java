package com.emprestimos.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emprestimos.dto.PessoaDTO;
import com.emprestimos.exception.CustomException;
import com.emprestimos.indicator.TipoIdentificador;
import com.emprestimos.interfaces.TipoIndicadorStrategy;
import com.emprestimos.model.Pessoa;
import com.emprestimos.repository.EmprestimoRepository;
import com.emprestimos.repository.PessoaRepository;
import com.emprestimos.strategies.AposentadoStrategy;
import com.emprestimos.strategies.EstudanteUniversitarioStrategy;
import com.emprestimos.strategies.PessoaFisicaStrategy;
import com.emprestimos.strategies.PessoaJuridicaStrategy;

@Service
public class PessoaService {

	private final static HashMap<TipoIdentificador, TipoIndicadorStrategy> strategies = new HashMap<>();

	public PessoaService() {
		strategies.put(TipoIdentificador.PF, new PessoaFisicaStrategy());
		strategies.put(TipoIdentificador.AP, new AposentadoStrategy());
		strategies.put(TipoIdentificador.EU, new EstudanteUniversitarioStrategy());
		strategies.put(TipoIdentificador.PJ, new PessoaJuridicaStrategy());
	}

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public List<PessoaDTO> listPessoas() {
		return pessoaRepository.findAll().stream()
				.map(this::convertEntityToDTO)
				.collect(Collectors.toList());
	}

	public Pessoa listPessoaById(Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new CustomException("Pessoa não encontrada", HttpStatus.UNPROCESSABLE_ENTITY));
	}

	public void createPessoa(PessoaDTO pessoaDTO) {
		if (pessoaRepository.findByIdentificador(pessoaDTO.getIdentificador()).isPresent())
			throw new CustomException("Já existe pessoa cadastrada com esse identificador.", HttpStatus.UNPROCESSABLE_ENTITY);

		getStrategy(pessoaDTO.getTipoIdentificador()).validarIdentificador(pessoaDTO.getIdentificador());
		pessoaRepository.save(convertDTOToEntity(pessoaDTO));
	}

	public void updatePessoaById(Long pessoaId, PessoaDTO pessoaDTO) {
		Pessoa pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new CustomException("Pessoa não encontrada", HttpStatus.UNPROCESSABLE_ENTITY));

		if (pessoaDTO.getNome() != null
				&& !pessoaDTO.getNome().isBlank()
				&& !pessoa.getNome().equals(pessoaDTO.getNome())) pessoa.setNome(pessoaDTO.getNome());

		if (pessoaDTO.getDataNascimento() != null && pessoa.getDataNascimento() != pessoaDTO.getDataNascimento()) pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
		pessoaRepository.save(pessoa);
	}

	public void deletePessoaById(Long pessoaId) {
		if (!emprestimoRepository.findEmprestimoByPessoaId(pessoaId).isEmpty())
			throw  new CustomException("Esta pessoa não pode ser deletada porque possui emprestimos.", HttpStatus.UNPROCESSABLE_ENTITY);

		pessoaRepository.deleteById(pessoaId);
	}

	private PessoaDTO convertEntityToDTO(Pessoa pessoa) {
		return PessoaDTO.builder()
				.id(pessoa.getId())
				.nome(pessoa.getNome())
				.identificador(pessoa.getIdentificador())
				.tipoIdentificador(TipoIdentificador.valueOf(pessoa.getTipoIdentificador()))
				.valorMinimoMensal(pessoa.getValorMinimoMensal())
				.valorMaximoEmprestimo(pessoa.getValorMaximoEmprestimo())
				.dataNascimento(pessoa.getDataNascimento())
				.build();
	}

	private Pessoa convertDTOToEntity(PessoaDTO pessoaDTO) {
		return Pessoa.builder()
				.id(pessoaDTO.getId())
				.nome(pessoaDTO.getNome())
				.identificador(pessoaDTO.getIdentificador())
				.tipoIdentificador(pessoaDTO.getTipoIdentificador().name())
				.valorMinimoMensal(getStrategy(pessoaDTO.getTipoIdentificador()).getValorMinimoMensalParcelas())
				.valorMaximoEmprestimo(getStrategy(pessoaDTO.getTipoIdentificador()).getValorMaximoEmprestimo())
				.dataNascimento(pessoaDTO.getDataNascimento())
				.build();
	}

	private TipoIndicadorStrategy getStrategy(TipoIdentificador tipoIdentificador) {
		return strategies.get(tipoIdentificador);
	}

	public Pessoa findByIdentificador(String identificador) {
		return pessoaRepository.findByIdentificador(identificador)
				.orElseThrow(() -> new CustomException("Pessoa não encontrada para o identificador: " + identificador, HttpStatus.UNPROCESSABLE_ENTITY));
	}
}
