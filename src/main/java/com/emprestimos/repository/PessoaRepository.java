package com.emprestimos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.emprestimos.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	List<Pessoa> findAll();
	Optional<Pessoa> findByIdentificador(String identificador);
}
