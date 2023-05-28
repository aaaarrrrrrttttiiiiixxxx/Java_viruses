package egorov.model;

import egorov.build.Component;

import java.util.ArrayList;

@Component
public class CityDAO {
    private ArrayList<City> cities;

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
