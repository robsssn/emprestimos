package com.emprestimos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emprestimos.dto.EmprestimoDTO;
import com.emprestimos.service.EmprestimoService;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;

	@GetMapping("/{identificadorPessoa}")
	public ResponseEntity listEmprestimosByIdentificadorPessoa(@PathVariable String identificadorPessoa) {
		return ResponseEntity.ok(emprestimoService.listEmprestimosByIdentificadorPessoa(identificadorPessoa));
	}

	@PostMapping("/{identificadorPessoa}")
	public ResponseEntity realizarEmprestimo(@PathVariable String identificadorPessoa ,@Valid @RequestBody EmprestimoDTO emprestimoDTO) throws Exception {
		emprestimoService.realizarEmprestimo(identificadorPessoa, emprestimoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{idEmprestimo}/pagamento")
	public ResponseEntity pagarEmprestimo(@PathVariable Long idEmprestimo) {
		emprestimoService.pagarEmprestimo(idEmprestimo);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
