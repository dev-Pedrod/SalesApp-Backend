package com.devpedrod.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devpedrod.cursomc.domain.Cliente;
import com.devpedrod.cursomc.repositories.ClienteRepository;
import com.devpedrod.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		
		String newPass = NewPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String NewPassword() {
		char[] vet = new char[16];
		for(int i = 0; i<vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { //gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if(opt == 1) { //gera uma letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { //gera uma letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
