package utez.edu.mx.backend.utils.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htlmMsg = "<h3><b> Servicio clínico </b></h3><br>" +
                    text + "<br><hr style='border-color:rgb(0,171,132);'/><p style='text-align:justify;'>Si no solicitaste este cambio, ignora el correo.</p>" +
                    "<p style='text-align:justify;'>Esta cuenta de correo no es supervisada, no responder este mensaje.</p>";
            helper.setFrom("noreply@edwin.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htlmMsg, true);
            emailSender.send(mimeMessage);
        } catch (Exception e) {
        }
    }
}
