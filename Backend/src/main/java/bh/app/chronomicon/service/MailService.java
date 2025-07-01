package bh.app.chronomicon.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Value ("${spring.mail.username}")
    private String senderMail;

    public void sendPasswordRecoveryEmail(String receiverMail, String receiverName, String resetLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage ();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper (message, true, "UTF-8");
        mimeMessageHelper.setTo(receiverMail);
        mimeMessageHelper.setSubject ("Recuperação de Senha");
        mimeMessageHelper.setFrom(senderMail, "TREM - APP BH");
        String htmlContent = loadResetPasswordTemplate (resetLink, receiverName);
        mimeMessageHelper.setText (htmlContent, true);
        javaMailSender.send (message);
    }

    private String loadTemplate(String template) throws IOException{
        ClassPathResource resource = new ClassPathResource ("templates/mail/"+template);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader (resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private String loadResetPasswordTemplate(String resetLink, String name){
        try{
            String template = loadTemplate ("PasswordRecovery.html");
            template = template.replace ("{{NOME}}", name);
            template = template.replace ("{{URL_DE_REDEFINICAO}}", resetLink);
            return template;
        } catch (IOException e) {
            log.error ("Erro ao gerar email de redefinição de senha {}: {}", name, e.getMessage ());
            throw new RuntimeException ("Erro ao gerar email de redefinição de senha.");
        }
    }
}
