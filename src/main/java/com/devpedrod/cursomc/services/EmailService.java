package com.devpedrod.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.devpedrod.cursomc.domain.Pedido;

public interface EmailService {
 
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
