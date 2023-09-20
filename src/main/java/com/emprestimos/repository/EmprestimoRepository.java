package com.emprestimos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.emprestimos.model.Emprestimo;

public interface EmprestimoRepository extends CrudRepository <Emprestimo, Long> {

	@Query("FROM Emprestimo WHERE pessoa.identificador = :identificadorPessoa ORDER BY dataCriacao DESC")
	List<Emprestimo> findEmprestimosByIdentificadorPessoa(String identificadorPessoa);

	@Query("FROM Emprestimo WHERE pessoa.id = :pessoaId")
	List<Emprestimo> findEmprestimoByPessoaId(Long pessoaId);
}
