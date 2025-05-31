package com.smartcompare.email.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Dirección “from” que se utilizará en el correo.
     * Se inyecta desde application.properties: spring.mail.properties.mail.from
     */
    @Value("${spring.mail.properties.mail.from}")
    private String fromAddress;

    /**
     * Envía un correo HTML basado en una plantilla Thymeleaf.
     *
     * @param to           La dirección de destino (email).
     * @param subject      Asunto del correo.
     * @param templateName Nombre de la plantilla (sin la extensión .html).
     *                     Ejemplo: "email/confirmation" (apunta a /templates/email/confirmation.html).
     * @param context      Contexto Thymeleaf con las variables necesarias para la plantilla.
     */
    public void sendHtmlEmail(String to, String subject, String templateName, Context context) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo HTML", e);
        }
    }
}
