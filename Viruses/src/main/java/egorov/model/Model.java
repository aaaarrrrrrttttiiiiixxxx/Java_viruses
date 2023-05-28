package egorov.model;

import java.util.Date;
import java.util.List;

public class Model {
    private int id = 0;
    private Season season;
    private long money;
    private int vaccinationCost;
    private List<City> cities;
    private int modelingPeriod; // в месяцах
    private Date startDate;
    private Date currentDate;

    public Model() {
    }

    public Model(long money, int vaccinationCost, List<City> cities, int modelingPeriod, Date startDate) {
        this.money = money;
        this.vaccinationCost = vaccinationCost;
        this.cities = cities;
        this.modelingPeriod = modelingPeriod;
        this.startDate = startDate;
        currentDate = startDate;
        updateSeason();
    }

    public Model(long money, int vaccinationCost, List<City> cities, int modelingPeriod, Date startDate, Date currentDate) {
        this.money = money;
        this.vaccinationCost = vaccinationCost;
        this.cities = cities;
        this.modelingPeriod = modelingPeriod;
        this.startDate = startDate;
        this.currentDate = currentDate;
        updateSeason();
    }

    private void updateSeason(){
        if(startDate.getMonth() > 1 && startDate.getMonth() < 5)
            season = Season.SPRING;
        else if(startDate.getMonth() > 4 && startDate.getMonth() < 8)
            season = Season.SUMMER;
        else if(startDate.getMonth() > 7 && startDate.getMonth() < 11)
            season = Season.SUMMER;
        else
            season = Season.WINTER;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
        updateSeason();
    }

    public Season getSeason() {
        return season;
    }

    public long getMoney() {
        return money;
    }

    public int getVaccinationCost() {
        return vaccinationCost;
    }

    public void setVaccinationCost(int vaccinationCost) {
        this.vaccinationCost = vaccinationCost;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getModelingPeriod() {
        return modelingPeriod;
    }

    public void setModelingPeriod(int modelingPeriod) {
        this.modelingPeriod = modelingPeriod;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(season + " money: " + money + " vaccination cost: " + vaccinationCost + " modeling period: " + modelingPeriod +
                " start date: " + startDate + " current date: " + currentDate + "\n");

        for (City c : cities) {
            res.append(c.toString()).append("\n");
        }
        return res.toString();
    }

    public String toCSVString(String CSV_SEPARATOR){
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(money);
        stringBuffer.append(CSV_SEPARATOR);
        stringBuffer.append(vaccinationCost);
        stringBuffer.append(CSV_SEPARATOR);
        stringBuffer.append(modelingPeriod);
        stringBuffer.append(CSV_SEPARATOR);
        stringBuffer.append(startDate);
        stringBuffer.append(CSV_SEPARATOR);
        stringBuffer.append(currentDate);
        for (City c :cities) {
            stringBuffer.append("\n");
            stringBuffer.append(c.toCSVString(CSV_SEPARATOR));
        }
        return stringBuffer.toString();
    }
}
