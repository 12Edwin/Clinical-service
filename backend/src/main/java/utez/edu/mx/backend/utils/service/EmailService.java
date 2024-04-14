package utez.edu.mx.backend.utils.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import javax.mail.internet.MimeMessage;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
    }

    public ResponseEntity<?> sendSimpleMessage(String to, String subject, String html) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htlmMsg = "<h3><b> Servicio clínico </b></h3><br>" +
                    html + "<br><hr style='border-color:rgb(0,171,132);'/><p style='text-align:justify;'>Si no solicitaste este cambio, ignora el correo.</p>" +
                    "<p style='text-align:justify;'>Esta cuenta de correo no es supervisada, no responder este mensaje.</p>";
            ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
            String host = resourceBundle.getString("spring.mail.username");
            helper.setFrom(host);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htlmMsg, true);
            emailSender.send(mimeMessage);
            return new ResponseEntity<>(new Message("Mail sended", TypeResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(new Message("Mail not sended", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
