package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail( String email, String subject, String body) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;

            helper = new MimeMessageHelper(mimeMessage,true, "utf-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom("plantExpert2020@gmail.com");
            helper.setText(body,true);

            javaMailSender.send(mimeMessage);
        } catch ( MessagingException e) {
            e.printStackTrace();
        }

    }

    public String prepareActivationEmailBody(Context context){
        return templateEngine.process("RegisterActivationTemplate", context);
    }

    public String prepareContactMessageAnswerEmail(Context context){
        return templateEngine.process("AnswerEmailTemplate", context);
    }

    public Context prepareAnswerEmailContext(String header, String content, String answer, String adminFirstName, String adminLastName){
        Context context = new Context();
        context.setVariable("header", header);
        context.setVariable("content", content);
        context.setVariable("answer", answer);
        context.setVariable("adminFirstName", adminFirstName);
        context.setVariable("adminLastName", adminLastName);
        return context;
    }

    public static Context prepareEmail(String tokenLink){

        Context context = new Context();
        context.setVariable("tokenLink", tokenLink);
        return context;

    }




}
