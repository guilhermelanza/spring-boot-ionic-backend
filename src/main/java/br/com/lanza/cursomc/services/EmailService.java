package br.com.lanza.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.lanza.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

}
