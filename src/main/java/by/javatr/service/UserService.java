package by.javatr.ibank.service;

import by.javatr.ibank.bean.Category;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface UserService {
    void deleteUser(long id) throws ServiceSourceException;

    void createUser(String userLogin)
                throws ServiceValidationException, ServiceSourceException;

    long loadUser(String name)
                throws ServiceValidationException, ServiceSourceException;

    User getUserById(long id) throws ServiceSourceException;

    void updateIncome(long id, double income)
                throws ServiceValidationException, ServiceSourceException;

    List<Category> getAllCategories(long userId) throws ServiceSourceException;

    List<Category> getAllCategoriesByDayExpense(long userId)
                throws ServiceSourceException;

    void createCategory(String categoryName, long userId)
                throws ServiceValidationException, ServiceSourceException;

    void updateCategory(long userId, String categoryName, double expense)
                throws ServiceValidationException, ServiceSourceException;

    boolean deleteCategory(long userId, String categoryName)
                throws ServiceSourceException;

    Category getCategory(long userId, String categoryName)
                throws ServiceSourceException, ServiceValidationException;
}

