package edu.egg.tinder.Main.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Service
public class NotificacionServicio {

    JavaMailSender javaMailSender = new JavaMailSender() {
        @Override
        public MimeMessage createMimeMessage() {
            return null;
        }

        @Override
        public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
            return null;
        }

        @Override
        public void send(MimeMessage mimeMessage) throws MailException {

        }

        @Override
        public void send(MimeMessage... mimeMessages) throws MailException {

        }

        @Override
        public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

        }

        @Override
        public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

        }

        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {

        }

        @Override
        public void send(SimpleMailMessage... simpleMessages) throws MailException {

        }
    };
    @Async
    public void enviarMail(String cuerpo, String titulo, String mailUsuario){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mailUsuario);
        mensaje.setFrom("noreply@tinder-mascota.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);

        javaMailSender.send(mensaje);
    }

}
