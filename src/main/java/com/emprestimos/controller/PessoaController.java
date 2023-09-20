package com.emprestimos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emprestimos.dto.PessoaDTO;
import com.emprestimos.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public ResponseEntity listPessoas() {
		return ResponseEntity.ok(pessoaService.listPessoas());
	}

	@GetMapping(value = "/{pessoaId}")
	public ResponseEntity listPessoaById(@PathVariable Long pessoaId) throws Exception {
		return ResponseEntity.ok(pessoaService.listPessoaById(pessoaId));
	}

	@PostMapping
	public ResponseEntity createPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
		pessoaService.createPessoa(pessoaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{pessoaId}")
	public ResponseEntity updatePessoaById(@PathVariable Long pessoaId, @RequestBody PessoaDTO pessoaDTO) throws Exception {
		pessoaService.updatePessoaById(pessoaId, pessoaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{pessoaId}")
	public ResponseEntity deletePessoaById(@PathVariable Long pessoaId) {
		pessoaService.deletePessoaById(pessoaId);
		return ResponseEntity.ok().build();
	}
}
