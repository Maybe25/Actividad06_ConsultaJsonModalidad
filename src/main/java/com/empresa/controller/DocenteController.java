package com.empresa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Docente;
import com.empresa.entity.FiltroDocente;
import com.empresa.service.DocenteService;
import com.empresa.util.Constantes;

@RestController
@RequestMapping("/rest/docente")
@CrossOrigin(origins = "http://localhost:4200")
public class DocenteController {

	@Autowired
	private DocenteService docenteService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Docente>> listaDocente() {
		List<Docente> lista = docenteService.listaDocente();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaDocente(@RequestBody Docente obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Docente objSalida = docenteService.insertaActualizaDocente(obj);
			if (objSalida == null) {
				salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", Constantes.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	
	@GetMapping("/porDni/{paramDni}")
	@ResponseBody
	public ResponseEntity<List<Docente>> listaPorDni(@PathVariable("paramDni") String dni) {
		List<Docente> lista = docenteService.buscarPorDni(dni);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/porNombre/{prefix}")
	@ResponseBody
	public ResponseEntity<List<Docente>> listaPorNombreContiene(@PathVariable("prefix") String prefix) {
		List<Docente> lista = docenteService.buscarPorNombre(prefix);
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/porDniNombre/{paramDni}/{paramNombre}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaPorDniNombreSimple(
			@PathVariable("paramDni")String dni,
			@PathVariable("paramNombre")String nombre) {
		Map<String,Object> salida = new HashMap<String,Object>();
		List<Docente> lista = docenteService.buscarPorNombreDni(dni, "%"+ nombre+"%");
		if (CollectionUtils.isEmpty(lista)) {
			salida.put("mensaje", "No exite datos para consulta");	
		}else {
			salida.put("mensaje", "La consulta tiene " + lista.size() + " elementos");	
			salida.put("lista", lista);
		}
		return ResponseEntity.ok(salida);

	}
	
	@GetMapping("/porDniNombreConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaPorDniNombreParametros(
			@RequestParam("dni")String dni,
			@RequestParam(value = "nombre", required = false, defaultValue = "")String nombre) {
		Map<String,Object> salida = new HashMap<String,Object>();
		
		try {
			List<Docente> lista = docenteService.buscarPorNombreDni(dni, "%"+ nombre+"%");
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No exite datos para consulta");	
			}else {
				salida.put("mensaje", "La consulta tiene " + lista.size() + " elementos");	
				salida.put("lista", lista);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error " + e.getMessage());
		}	
		
		return ResponseEntity.ok(salida);

	}
	
	@PostMapping("/porDniNombreConParametrosJson")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaPorDniNombreParametrosJson(
			@RequestBody Docente doc) {
		Map<String,Object> salida = new HashMap<String,Object>();
		
		try {
			List<Docente> lista = docenteService.buscarPorNombreDni(doc.getDni(), "%"+ doc.getNombre()+"%");
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No exite datos para consulta");	
			}else {
				salida.put("mensaje", "La consulta tiene " + lista.size() + " elementos");	
				salida.put("lista", lista);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error " + e.getMessage());
		}	
		
		return ResponseEntity.ok(salida);

	}
	
	@PostMapping("/porDniNombreConParametrosJsonClase")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaPorDniNombreParametrosJsonClase(
			@RequestBody FiltroDocente filtro) {
		Map<String,Object> salida = new HashMap<String,Object>();
		
		try {
			filtro.setNombre("%"+filtro.getNombre()+"%");
			List<Docente> lista = docenteService.listaPorFiltro(filtro);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No exite datos para consulta");	
			}else {
				salida.put("mensaje", "La consulta tiene " + lista.size() + " elementos");	
				salida.put("lista", lista);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error " + e.getMessage());
		}	
		
		return ResponseEntity.ok(salida);

	}
}
