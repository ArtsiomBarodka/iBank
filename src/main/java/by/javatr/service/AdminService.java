package by.javatr.ibank.service;

import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface AdminService {
    int countAllUsers() throws ServiceSourceException;

    void sendAllUsersMessage(String adminEmail, String password, String message)
                throws ServiceSourceException, ServiceValidationException;
}
