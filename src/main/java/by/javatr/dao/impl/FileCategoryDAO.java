package by.javatr.ibank.dao.impl;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Category;
import by.javatr.ibank.dao.CategoryDAO;
import by.javatr.ibank.dao.exception.DAOException;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class FileCategoryDAO implements CategoryDAO {
    private static final String DELIMITER = " ";
    private String basePath;

    public FileCategoryDAO(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void createCategory(Category category) throws DAOException {
        try(FileWriter writer = new FileWriter(basePath, true)){
            category.setId(UUID.randomUUID().getMostSignificantBits()
                        & Long.MAX_VALUE);

            writer.write(parseCategory(category));
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }
    }

    @Override
    public List<Category> getAllByUserId(long userId) throws DAOException {
        List<Category> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                long readingUserId = Long.parseLong(line.split(DELIMITER)[1]
                            .substring("USER_ID=".length()));

                if(readingUserId == userId){
                    Category category = parseLine(line);

                    result.add(category);
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }

        return result;
    }

    @Override
    public void updateCategory(Category category) throws DAOException {
        StringBuilder temp = new StringBuilder();
        File inputFile = new File(basePath);
        File tempFile = new File(Constants.TEMP_FILE);

        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            throw new DAOException("File is not exist", e);
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(tempFile)){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    temp.append(line);
                    continue;
                }

                long readingId = Long.parseLong(line.split(DELIMITER)[0]
                            .substring("ID=".length()));

                if(readingId == category.getId()){
                    String newLine = parseCategory(category);

                    temp.append(newLine);
                } else {
                    temp.append(line).append("\n");
                }
            }

            writer.write(temp.toString());

        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    @Override
    public boolean deleteCategory(long userId, String categoryName)
                throws DAOException {
        StringBuilder temp = new StringBuilder();

        File inputFile = new File(basePath);
        File tempFile = new File(Constants.TEMP_FILE);

        boolean result = false;

        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            throw new DAOException("File is not exist", e);
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(tempFile)){
            String line;

            while ((line = reader.readLine()) != null) {

                if(line.isEmpty()){
                    temp.append(line);
                    continue;
                }

                long readingUserId = Long.parseLong(line.split(DELIMITER)[1]
                            .substring("USER_ID=".length()));

                String readingName = line.split(DELIMITER)[2]
                            .substring("NAME=".length());

                if (readingUserId == userId &&
                            readingName.equalsIgnoreCase(categoryName)) {
                    result = true;
                    continue;
                }

                temp.append(line).append("\n");

            }

            writer.write(temp.toString());
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        return result;
    }

    @Override
    public void deleteAllByUserId(long userId) throws DAOException {
        StringBuilder temp = new StringBuilder();
        File inputFile = new File(basePath);
        File tempFile = new File(Constants.TEMP_FILE);

        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            throw new DAOException("File is not exist", e);
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(tempFile)){
            String line;

            while ((line = reader.readLine()) != null) {

                if(line.isEmpty()){
                    temp.append(line);
                    continue;
                }

                long readingUserId = Long.parseLong(line.split(DELIMITER)[1]
                            .substring("USER_ID=".length()));

                if (readingUserId == userId) {
                    continue;

                }

                temp.append(line).append("\n");
            }

            writer.write(temp.toString());
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    @Override
    public Category getCategory(long userId, String categoryName)
                throws DAOException {
        Category category = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                long readingUserId = Long.parseLong(line.split(DELIMITER)[1]
                            .substring("USER_ID=".length()));

                String readingName = line.split(DELIMITER)[2]
                            .substring("NAME=".length());

                if(readingUserId == userId &&
                            readingName.equalsIgnoreCase(categoryName)){
                    category = parseLine(line);
                    break;
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        return category;
    }

    private String parseCategory(Category category){
        StringBuilder text = new StringBuilder();

        text.append("ID=").append(category.getId()).append(DELIMITER);
        text.append("USER_ID=").append(category.getUserId()).append(DELIMITER);
        text.append("NAME=").append(category.getName()).append(DELIMITER);
        text.append("DAY_EXPENSE=").append(category.getDayExpense()).append(DELIMITER);
        text.append("MONTH_EXPENSE=").append(category.getMonthExpense())
                    .append(DELIMITER);
        text.append("TIMESTAMP=").append(category.getTimestamp().getTime())
                    .append(DELIMITER);
        text.append("\n");

        return text.toString();
    }

    private Category parseLine(String line){
        String[] fields = line.split(DELIMITER);

        long id = Long.parseLong(fields[0].substring("ID=".length()));
        long userId = Long.parseLong(fields[1].substring("USER_ID=".length()));
        String name = fields[2].substring("NAME=".length());
        double dayExpense = Double.parseDouble(fields[3]
                    .substring("DAY_EXPENSE=".length()));
        double monthExpense = Double.parseDouble(fields[4]
                    .substring("MONTH_EXPENSE=".length()));
        Timestamp timestamp = new Timestamp(Long.parseLong(fields[5]
                    .substring("TIMESTAMP=".length())));

        Category category = new Category();
        category.setId(id);
        category.setUserId(userId);
        category.setName(name);
        category.setDayExpense(dayExpense);
        category.setMonthExpense(monthExpense);
        category.setTimestamp(timestamp);

        return category;
    }
}
