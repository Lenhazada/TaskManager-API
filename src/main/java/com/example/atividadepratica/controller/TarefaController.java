package com.example.atividadepratica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.atividadepratica.model.Tarefa;
import com.example.atividadepratica.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefas") // prefixo dos endpoints
public class TarefaController {
	private final TarefaRepository tarefaRepository;

	public TarefaController(TarefaRepository tarefaRepository) {
		this.tarefaRepository = tarefaRepository;
	}

	// Criando tarefa
	@PostMapping
	public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}

	// Consultando todas as tarefas
	@GetMapping
	public List<Tarefa> listarTarefas() {
		return tarefaRepository.findAll();
	}

	// Buscando tarefa pelo id
	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
		return tarefaRepository.findById(id).map(ResponseEntity::ok) // Encontrando algo retorna 200
				.orElse(ResponseEntity.notFound().build()); // Se não encontrar nada retorna 404
	}

	// Alterando tarefa selecionada pelo id
	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
		return tarefaRepository.findById(id).map(tarefa -> {
			// Altera e atuliza os campos
			tarefa.setNome(tarefaAtualizada.getNome());
			tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
			tarefa.setResponsavel(tarefaAtualizada.getResponsavel());
			Tarefa atualizada = tarefaRepository.save(tarefa);
			return ResponseEntity.ok(atualizada);
		}).orElse(ResponseEntity.notFound().build());
	}

	// Deletando tarefa pelo id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
		return tarefaRepository.findById(id).map(tarefa -> {
			tarefaRepository.delete(tarefa); // Apaga a tarefa com base no id fornecido
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
