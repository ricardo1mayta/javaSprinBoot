package pe.com.avivel.sistemas.guiaselectronicas.service.spec.util;

import org.springframework.core.io.Resource;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendMail(String[] to, String subject, String body, Resource[] resources) throws MessagingException, UnsupportedEncodingException;

    void sendHtmlMessage(String[] to, String subject, String htmlBody, Resource[] resources) throws MessagingException, UnsupportedEncodingException;

    void sendHtmlMessage(String[] to, String subject, Context context, String plantilla, Resource[] resources) throws MessagingException, UnsupportedEncodingException;
}
