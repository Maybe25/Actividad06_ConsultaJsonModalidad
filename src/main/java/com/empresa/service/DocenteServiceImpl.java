package com.empresa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.entity.Docente;
import com.empresa.entity.FiltroDocente;
import com.empresa.repository.DocenteRepository;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	private DocenteRepository repository;

	@Override
	public List<Docente> listaDocente() {
		return repository.findAll();
	}

	@Override
	public Docente insertaActualizaDocente(Docente obj) {
		return repository.save(obj);
	}

	@Override
	public List<Docente> buscarPorDni(String dni) {
		
		
		return repository.findByDni(dni);
	}

	@Override
	public List<Docente> buscarPorNombre(String prefix) {
		return repository.findByNombreContaining(prefix);
	}

	@Override
	public List<Docente> buscarPorNombreDni(String dni, String nombre) {
		return repository.listaPorDniNombre(dni, nombre);
	}

	@Override
	public List<Docente> listaPorFiltro(FiltroDocente filtro) {
		return repository.listaPorFiltro(filtro);
	}

}
