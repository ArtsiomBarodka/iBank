package by.javatr.ibank.service.impl;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Category;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.dao.CategoryDAO;
import by.javatr.ibank.dao.UserDAO;
import by.javatr.ibank.dao.exception.DAOException;
import by.javatr.ibank.dao.factory.DAOFactory;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.validation.ServiceValidator;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserServiceImpl implements UserService {

    @Override
    public void deleteUser(long id) throws ServiceSourceException {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getFileUserDAO();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            userDAO.deleteUser(id);
            categoryDAO.deleteAllByUserId(id);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public void createUser(String userLogin)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(userLogin)){
            throw new ServiceValidationException("Incorrect login!");
        }

        User user = new User();
        user.setName(userLogin);
        user.setMonthExpense(0);
        user.setDayExpense(0);
        user.setIncome(0);
        user.setTimestamp(new Timestamp(System.currentTimeMillis()));

        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getFileUserDAO();

            userDAO.saveUser(user);

            long userId = userDAO.getUserByName(userLogin).getId();

            createStandardCategories(userId);

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

    }

    @Override
    public long loadUser(String name)
            throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(name)){
            throw new ServiceValidationException("Incorrect name");
        }

        User user = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getFileUserDAO();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            user = userDAO.getUserByName(name);
            user.setCategories(categoryDAO.getAllByUserId(user.getId()));

            updateExpenseByTime(user);
            userDAO.updateUser(user);

            for (Category c: user.getCategories()) {
                categoryDAO.updateCategory(c);
            }

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return user.getId();
    }

    @Override
    public User getUserById(long id) throws ServiceSourceException {
        User user = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getFileUserDAO();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            user = userDAO.getUserById(id);

            user.setCategories(categoryDAO.getAllByUserId(user.getId()));

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return user;
    }

    @Override
    public void updateIncome(long id, double income)
                throws ServiceValidationException, ServiceSourceException {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getFileUserDAO();

            User user = userDAO.getUserById(id);

            if(user == null){
                throw new ServiceValidationException("Wrong user id.");
            }

            user.setIncome(user.getIncome() + income);

            userDAO.updateUser(user);

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public List<Category> getAllCategories(long userId)
                throws ServiceSourceException {
        List<Category> categories = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            categories = categoryDAO.getAllByUserId(userId);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return categories;
    }

    @Override
    public List<Category> getAllCategoriesByDayExpense(long userId)
                throws ServiceSourceException {
        List<Category> categories = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            categories = categoryDAO.getAllByUserId(userId);

            categories.removeIf((category) -> category.getDayExpense() == 0);

        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return categories;
    }

    @Override
    public void createCategory(String categoryName, long userId)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidCategory(categoryName)){
            throw new ServiceValidationException("Incorrect name of category!");
        }


        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            Category category = categoryDAO.getCategory(userId, categoryName);

            if(category != null){
                throw new ServiceValidationException("Category already exists!");
            }

            category = new Category();
            category.setName(categoryName);
            category.setDayExpense(0);
            category.setMonthExpense(0);
            category.setTimestamp(new Timestamp(System.currentTimeMillis()));
            category.setUserId(userId);

            categoryDAO.createCategory(category);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public void updateCategory(long userId, String categoryName, double expense)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidCategory(categoryName)){
            throw new ServiceValidationException("Incorrect name of category");
        }

        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();
            UserDAO userDAO = factory.getFileUserDAO();

            Category category = getCategory(userId, categoryName);
            User user = userDAO.getUserById(userId);

            category.setDayExpense(category.getDayExpense() + expense);
            category.setMonthExpense(category.getMonthExpense() + expense);
            category.setTimestamp(new Timestamp(System.currentTimeMillis()));

            user.setDayExpense(user.getDayExpense() + expense);
            user.setMonthExpense(user.getMonthExpense() + expense);
            user.setIncome(user.getIncome() - expense);

            categoryDAO.updateCategory(category);
            userDAO.updateUser(user);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

    }

    @Override
    public boolean deleteCategory(long userId, String categoryName)
                throws ServiceSourceException {
        boolean isDeletedCategory = false;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            isDeletedCategory = categoryDAO.deleteCategory(userId, categoryName);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
        return isDeletedCategory;
    }


    public Category getCategory(long userId, String categoryName)
                throws ServiceSourceException, ServiceValidationException {
        if(!ServiceValidator.isValidCategory(categoryName)){
            throw new ServiceValidationException("Incorrect name of category");
        }
        Category category = null;

        try {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO categoryDAO = factory.getFileCategoryDAO();

            category = categoryDAO.getCategory(userId, categoryName);

            if(category == null){
                category = getCategory(userId, Constants.StandardCategorise.OTHER.getName());
            }
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

        return category;
    }

    private void createStandardCategories(long userId)
                throws ServiceSourceException, ServiceValidationException {
        for (Constants.StandardCategorise c : Constants.StandardCategorise.values()) {
            createCategory(c.getName(), userId);
        }
    }

    private void updateExpenseByTime(User user){
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();

        int dayNow = calendar.get(Calendar.DAY_OF_YEAR);
        int monthNow = calendar.get(Calendar.MONTH);

        calendar.setTime(user.getTimestamp());

        int lastMonthChange = calendar.get(Calendar.MONTH);


        if(lastMonthChange != monthNow){
            user.setTimestamp(new Timestamp(dayNow));
            user.setMonthExpense(0);
            user.setDayExpense(0);

            for (Category c : user.getCategories()) {
                c.setMonthExpense(0);
                c.setDayExpense(0);
                c.setTimestamp(new Timestamp(dayNow));
            }
        }

        int sumOfDayExpense = 0;

        for (Category c : user.getCategories()) {
            calendar.setTime(c.getTimestamp());

            int lastDayChange = calendar.get(Calendar.DAY_OF_YEAR);

            if(lastDayChange != dayNow){
                c.setDayExpense(0);
                c.setTimestamp(new Timestamp(dayNow));
            }

            sumOfDayExpense += c.getDayExpense();
        }

        user.setDayExpense(sumOfDayExpense);
    }


}
