package com.valdir.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valdir.helpdesk.domain.Chamado;
import com.valdir.helpdesk.domain.Cliente;
import com.valdir.helpdesk.domain.Tecnico;
import com.valdir.helpdesk.domain.enums.Perfil;
import com.valdir.helpdesk.domain.enums.Prioridade;
import com.valdir.helpdesk.domain.enums.Status;
import com.valdir.helpdesk.repositories.ChamadoRepository;
import com.valdir.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Valdir Cersar", "871.839.570-80", "valdir@gmail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Alanjxs", "025.984.083-14", "alanjxs@gmail.com", encoder.encode("123"));
		Tecnico tec3 = new Tecnico(null, "Vandame", "568.891.760-14", "vandame@gmail.com", encoder.encode("123"));
		Tecnico tec4 = new Tecnico(null, "Bruce Lee", "478.285.300-99", "bruce@gmail.com", encoder.encode("123"));
		Tecnico tec5 = new Tecnico(null, "Jack Chan", "753.790.970-90", "jack@gmail.com", encoder.encode("123"));
		
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "477.144.690-30", "linus@gmail.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Bill Gates", "225.634.540-85", "bill@gmail.com", encoder.encode("123"));
		Cliente cli3 = new Cliente(null, "Steve Jobs", "538.239.180-79", "steve@gmail.com", encoder.encode("123"));
		Cliente cli4 = new Cliente(null, "Elon Musk", "510.513.740-06", "elon@gmail.com", encoder.encode("123"));
		Cliente cli5 = new Cliente(null, "mark Zuckberg", "795.407.150-24", "mark@gmail.com", encoder.encode("123"));

		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.BAIXA , Status.ABERTO, "Chamado 02", "Segundo Chamado", tec2, cli2);
		Chamado c3 = new Chamado(null, Prioridade.ALTA , Status.ABERTO, "Chamado 03", "terceiro Chamado", tec3, cli3);
		Chamado c4 = new Chamado(null, Prioridade.BAIXA , Status.ANDAMENTO, "Chamado 04", "quarto Chamado", tec4, cli4);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "Chamado 05", "quinto Chamado", tec4, cli5);
		
		
		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
		
	}

}
