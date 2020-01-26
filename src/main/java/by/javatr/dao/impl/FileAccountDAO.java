package by.javatr.ibank.dao.impl;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Account;
import by.javatr.ibank.dao.AccountDAO;
import by.javatr.ibank.dao.exception.DAOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class FileAccountDAO implements AccountDAO {
    private static final String DELIMITER = " ";
    private String basePath;

    public FileAccountDAO(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public boolean hasAccount(String login) throws DAOException {
        boolean result = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                String readingLogin = line.split(DELIMITER)[1]
                            .substring("LOGIN=".length());

                if(readingLogin.equalsIgnoreCase(login)){
                    result = true;
                    break;
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        return result;
    }

    @Override
    public void registration(Account account) throws DAOException {
        try(FileWriter writer = new FileWriter(basePath, true)){
            account.setId(UUID.randomUUID().getMostSignificantBits()
                        & Long.MAX_VALUE);

            writer.write(parseAccount(account));
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }
    }

    @Override
    public boolean singIn(String login, int password) throws DAOException {
        boolean result = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                String readingLogin = line.split(DELIMITER)[1]
                            .substring("LOGIN=".length());

                int readingPassword = Integer.parseInt(line.split(DELIMITER)[2]
                            .substring("PASSWORD=".length()));

                if(readingLogin.equalsIgnoreCase(login)
                            && readingPassword == password){
                    result = true;
                    break;
                }
            }
        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        return result;
    }

    @Override
    public Account getAccountByName(String login) throws DAOException {
        Account account = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                String readingLogin = line.split(DELIMITER)[1]
                            .substring("LOGIN=".length());

                if(readingLogin.equalsIgnoreCase(login)){
                    account = parseLine(line);
                    break;
                }

            }

        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        return account;
    }

    @Override
    public boolean deleteAccount(String login, int password) throws DAOException {
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

                String readingLogin = line.split(DELIMITER)[1]
                            .substring("LOGIN=".length());

                int readingPassword = Integer.parseInt(line.split(DELIMITER)[2]
                            .substring("PASSWORD=".length()));

                if (readingLogin.equalsIgnoreCase(login)
                            && readingPassword == password) {
                    result = true;
                    continue;
                }

                temp.append(line).append("\n");
            }

            writer.write(temp.toString());

        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        } catch (NullPointerException e){
            throw new DAOException("Incorrect input", e);
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        return result;
    }

    @Override
    public List<String> getAllUsersEmails() throws DAOException {
        List<String> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(basePath))){
            String line;

            while ((line = reader.readLine()) != null){

                if(line.isEmpty()){
                    continue;
                }

                String readingEmail = line.split(DELIMITER)[3]
                            .substring("EMAIL=".length());

                Constants.Role role = Constants.Role.valueOf(line.split(DELIMITER)[4]
                            .substring("ROLE=".length()));

                if(role == Constants.Role.ADMIN){
                    continue;
                }

                result.add(readingEmail);
            }

        }catch (IOException e){
            throw new DAOException("File is not exist", e);
        }

        return result;
    }

    private String parseAccount(Account account){
        StringBuilder text = new StringBuilder();

        text.append("ID=").append(account.getId()).append(DELIMITER);
        text.append("LOGIN=").append(account.getLogin()).append(DELIMITER);
        text.append("PASSWORD=").append(account.getPassword()).append(DELIMITER);
        text.append("EMAIL=").append(account.getEmail()).append(DELIMITER);
        text.append("ROLE=").append(account.getRole()).append(DELIMITER);
        text.append("\n");

        return text.toString();
    }

    private Account parseLine(String line){
        String[] fields = line.split(DELIMITER);

        long id = Long.parseLong(fields[0].substring("ID=".length()));
        String login = fields[1].substring("LOGIN=".length());
        int password = Integer.parseInt(fields[2].substring("PASSWORD=".length()));
        String email = fields[3].substring("EMAIL=".length());
        Constants.Role role = Constants.Role.valueOf(fields[4]
                    .substring("ROLE=".length()));

        Account account = new Account();
        account.setId(id);
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(role);

        return account;
    }
}
