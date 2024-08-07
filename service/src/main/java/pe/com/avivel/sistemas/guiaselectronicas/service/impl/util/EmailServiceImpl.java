package pe.com.avivel.sistemas.guiaselectronicas.service.impl.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.util.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${app.mail.alias}")
    private String alias;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendMail(@NonNull String[] to, String subject, String body, Resource[] resources) throws MessagingException, UnsupportedEncodingException {
        log.info("@@@ Sending Email to {}...", Arrays.toString(to));
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(fromEmail, alias);
        helper.setTo(to);

        if(resources != null){
            for (Resource resource : resources) {
                helper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);
            }
        }

        mailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(@NonNull String[] to, String subject, String htmlBody, Resource[] resources) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        //ClassPathResource clr = new ClassPathResource(logoUbicacion);
        //helper.addAttachment(logoNombre, clr, PNG_MIME);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.setFrom(fromEmail, alias);
        helper.setTo(to);

        if(resources != null){
            for (Resource resource : resources) {
                helper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);
            }
        }
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(@NonNull String[] to, String subject, Context context, String plantilla, Resource[] resources) throws MessagingException, UnsupportedEncodingException {
        //context.setVariable(NOMBRE_SISTEMA_STRING, nombreSistema);
        String htmlBody = springTemplateEngine.process(plantilla, context);
        sendHtmlMessage(to, subject, htmlBody, resources);
    }
}
