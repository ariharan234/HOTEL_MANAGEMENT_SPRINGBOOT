package com.cafe.com.cafe.management.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.awt.SystemColor.text;

@Service
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimplemail(String to, String subject, String text, List<String> list ){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("ariharan234@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list!=null&& list.size()>0)
            message.setCc(getccArray(list));
       try{
           mailSender.send(message);
       }
       catch (Exception ex){
           ex.getCause();
       }



    }

    private String[] getccArray(List<String> list){
        String[] cc=new String[list.size()];
        for(int i=0;i<list.size();i++){
            cc[i]=list.get(i);
        }
        return cc;
    }

    public void ForgotPassword(String to,String subject,String password) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("ariharan234@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a><br><b>Please change the password immediately after using temporary password by give temporary password as Old password</b></p>";

        mimeMessage.setContent(htmlMsg,"text/html");
        mailSender.send(mimeMessage);
    }
}
