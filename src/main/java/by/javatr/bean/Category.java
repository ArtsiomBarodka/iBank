package by.javatr.ibank.bean;

import java.sql.Timestamp;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class Category extends AbstractBean {
    private static final long serialVersionUID = 6570650546546497959L;

    private long userId;
    private String name;
    private double dayExpense;
    private double monthExpense;
    private Timestamp timestamp;

    public Category() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDayExpense() {
        return dayExpense;
    }

    public void setDayExpense(double dayExpense) {
        this.dayExpense = dayExpense;
    }

    public double getMonthExpense() {
        return monthExpense;
    }

    public void setMonthExpense(double monthExpense) {
        this.monthExpense = monthExpense;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" +
                "id=" + getId() +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", dayExpense=" + dayExpense +
                ", monthExpense=" + monthExpense +
                ", timestamp=" + timestamp;
    }
}
