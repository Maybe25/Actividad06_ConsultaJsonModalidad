package com.empresa.service;

import java.util.List;

import com.empresa.entity.Docente;
import com.empresa.entity.FiltroDocente;

public interface DocenteService {

	public abstract List<Docente> listaDocente();

	public abstract Docente insertaActualizaDocente(Docente obj);
	
	public abstract List<Docente> buscarPorDni(String dni);
	
	public abstract List<Docente> buscarPorNombre(String prefix);
	
	public abstract List<Docente> buscarPorNombreDni(String dni,String nombre);
	
	public abstract List<Docente> listaPorFiltro(FiltroDocente filtro);

}
