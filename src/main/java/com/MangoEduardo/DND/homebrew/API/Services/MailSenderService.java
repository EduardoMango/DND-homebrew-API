package com.MangoEduardo.DND.homebrew.API.Services;

import com.MangoEduardo.DND.homebrew.API.Repositories.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MailSenderService {

    private final SendGrid sendGrid;
    private final UserRepository userRepository;

    public MailSenderService(SendGrid sendGrid, UserRepository userRepository) {
        this.sendGrid = sendGrid;
        this.userRepository = userRepository;
    }

    public void sendEmail(String to, String subject, String body) throws IOException {
        Email from = new Email("eduardomango08@gmail.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);
  }

    public void sendEmailToAllUsers(String subject, String body) {
        List<String> allEmails = userRepository.findAllEmails();// Retrieve all emails from the db.
        Logger logger = Logger.getAnonymousLogger();
        for (String email : allEmails) {
            try {
                sendEmail(email, subject, body);
            } catch (IOException e) {
            // Logs the exception
            logger.log(Level.SEVERE, "An exception was thrown when trying to send an email to " + email, e);
            }
        }
    }

  //    @Scheduled(fixedRate = 60000) // For testing only
//  @Scheduled(cron = "0 0 6 * * 1-5")
//  @Async
//  public void scheduledEmail() {
//          sendEmailToAllUsers("Asunto Programado", "Este es un correo enviado automáticamente a todos los usuarios.");
//      }
}
