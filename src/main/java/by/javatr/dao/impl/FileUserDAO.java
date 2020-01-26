package by.javatr.ibank.dao.impl;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.dao.UserDAO;
import by.javatr.ibank.dao.exception.DAOException;
import java.io.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class FileUserDAO implements UserDAO {
    private static final String DELIMITER = " ";
    private String basePath;

    public FileUserDAO(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveUser(User user) throws DAOException {
        try(FileWriter writer = new FileWriter(basePath, true)){
            user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

            writer.write(parseUser(user));
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }
    }

    @Override
    public User getUserByName(String name) throws DAOException {
        User user = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                String readingName = line.split(DELIMITER)[1]
                            .substring("NAME=".length());

                if(readingName.equalsIgnoreCase(name)){
                    user = parseLine(line);
                    break;
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        return user;
    }

    @Override
    public User getUserById(long id) throws DAOException {
        User user = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                long readingID = Long.parseLong(line.split(DELIMITER)[0]
                            .substring("ID=".length()));

                if(readingID == id){
                    user = parseLine(line);
                    break;
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }

        return user;
    }

    @Override
    public void updateUser(User user) throws DAOException {
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

                if(readingId == user.getId()){
                    String newLine = parseUser(user);

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
    public void deleteUser(long id) throws DAOException {
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

                long readingId = Long.parseLong(line.split(DELIMITER)[0]
                            .substring("ID=".length()));

                if (readingId == id) {
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

    private User parseLine(String line){
        String[] fields = line.split(DELIMITER);

        long id = Long.parseLong(fields[0].substring("ID=".length()));
        String name = fields[1].substring("NAME=".length());
        double income = Double.parseDouble(fields[2].substring("INCOME=".length()));
        double dayExpense = Double.parseDouble(fields[3]
                    .substring("DAY_EXPENSE=".length()));
        double monthExpense = Double.parseDouble(fields[4]
                    .substring("MONTH_EXPENSE=".length()));
        Timestamp timestamp = new Timestamp(Long.parseLong(fields[5]
                    .substring("TIMESTAMP=".length())));

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setIncome(income);
        user.setDayExpense(dayExpense);
        user.setMonthExpense(monthExpense);
        user.setTimestamp(timestamp);

        return user;
    }

    private String parseUser(User user){
        StringBuilder text = new StringBuilder();

        text.append("ID=").append(user.getId()).append(DELIMITER);
        text.append("NAME=").append(user.getName()).append(DELIMITER);
        text.append("INCOME=").append(user.getIncome()).append(DELIMITER);
        text.append("DAY_EXPENSE=").append(user.getDayExpense())
                    .append(DELIMITER);
        text.append("MONTH_EXPENSE=").append(user.getMonthExpense())
                    .append(DELIMITER);
        text.append("TIMESTAMP=").append(user.getTimestamp().getTime())
                    .append(DELIMITER);
        text.append("\n");

        return text.toString();
    }

}
