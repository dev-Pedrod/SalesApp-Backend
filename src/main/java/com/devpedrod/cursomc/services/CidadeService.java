package com.devpedrod.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpedrod.cursomc.domain.Cidade;
import com.devpedrod.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	public CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
}
