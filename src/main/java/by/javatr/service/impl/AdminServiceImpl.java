package by.javatr.ibank.service.impl;

import by.javatr.ibank.dao.AccountDAO;
import by.javatr.ibank.dao.exception.DAOException;
import by.javatr.ibank.dao.factory.DAOFactory;
import by.javatr.ibank.service.AdminService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.validation.ServiceValidator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminServiceImpl implements AdminService {
    @Override
    public int countAllUsers() throws ServiceSourceException {
        int result = 0;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            AccountDAO accountDAO = factory.getFileAccountDAO();
            result = accountDAO.getAllUsersEmails().size();

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return result;
    }

    @Override
    public void sendAllUsersMessage(String adminEmail, String password, String message)
                throws ServiceSourceException, ServiceValidationException {
        if(!ServiceValidator.isValidEmail(adminEmail)){
            throw new ServiceValidationException("Incorrect email!");
        }
        if(!ServiceValidator.isNumber(password)){
            throw new ServiceValidationException("Incorrect password!");
        }
        if (message == null && message.isEmpty()){
            throw new ServiceValidationException("Empty message!");
        }

        List<String> emails = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            AccountDAO accountDAO = factory.getFileAccountDAO();
            emails = accountDAO.getAllUsersEmails();

            for (String email : emails) {
                sendMail(adminEmail, email, message, password);
            }

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

    }

    private void sendMail(String from, String to, String text, String password)
                throws ServiceSourceException {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setText(text);

            Transport.send(message);


        } catch (MessagingException e){
            throw new ServiceSourceException("Can`t send message!", e);
        }
    }
}
