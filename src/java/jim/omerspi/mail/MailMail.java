/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.mail;

/**
 *
 * @author JIMMY
 */
import jim.omerspi.Context;
import org.springframework.mail.SimpleMailMessage;

public class MailMail {

  //private MailSender mailSender;
//
//   mailSender=Context.getMailSender();
    public void sendMail(String from, String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();
        //mailSender=Context.getMailSender();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
       Context.getMailSender().send(message);
    }
}
