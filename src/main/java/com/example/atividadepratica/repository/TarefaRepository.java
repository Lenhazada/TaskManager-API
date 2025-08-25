package com.example.atividadepratica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.atividadepratica.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
