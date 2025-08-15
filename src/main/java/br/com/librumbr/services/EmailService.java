package br.com.librumbr.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String email, String link){
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("no-reply@gmail.com");
            helper.setTo(email);
            helper.setSubject("Recuperação de Senha");
            String htmlContent = "<h1>Recuperação de Senha</h1>"
                    + "<p>Olá,</p>"
                    + "<p>Você solicitou a redefinição da sua senha. Por favor, clique no link abaixo para criar uma nova senha:</p>"
                    + "<p><a href=\"" + link + "\">Redefinir Minha Senha</a></p>"
                    + "<br>"
                    + "<p>Se você não solicitou esta alteração, por favor, ignore este e-mail.</p>"
                    + "<p>Atenciosamente,<br>Equipe do Librum</p>";
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
