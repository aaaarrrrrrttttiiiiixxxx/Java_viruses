package egorov.model;

public class City {
    private int id = 0;
    private CitySize citySize;
    private int population;
    private float cityTransportLinks;
    private float intraCityTransportLinks;
    private int sickOneWeak;
    private int sickTwoWeak;
    private int sickThreeWeak;
    private int vaccinatedOneWeak;
    private int vaccinatedTwoWeak;
    private int vaccinatedThreeWeak;
    private int vaccinated;

    public City() {
    }

    public City(int population,
                float cityTransportLinks,
                float intraCityTransportLinks,
                int sickOneWeak,
                int sickTwoWeak,
                int sickThreeWeak,
                int vaccinatedOneWeak,
                int vaccinatedTwoWeak,
                int vaccinatedThreeWeak,
                int vaccinated) {
        if(population < 1000)
            citySize = CitySize.TOWN;
        else if(population < 500000)
            citySize = CitySize.CITY;
        else
            citySize = CitySize.MEGAPOLIS;

        this.population = population;
        this.cityTransportLinks = cityTransportLinks;
        this.intraCityTransportLinks = intraCityTransportLinks;
        this.sickOneWeak = sickOneWeak;
        this.sickTwoWeak = sickTwoWeak;
        this.sickThreeWeak = sickThreeWeak;
        this.vaccinatedOneWeak = vaccinatedOneWeak;
        this.vaccinatedTwoWeak = vaccinatedTwoWeak;
        this.vaccinatedThreeWeak = vaccinatedThreeWeak;
        this.vaccinated = vaccinated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSickOneWeak() {
        return sickOneWeak;
    }

    public void setSickOneWeak(int sickOneWeak) {
        this.sickOneWeak = sickOneWeak;
    }

    public int getSickTwoWeak() {
        return sickTwoWeak;
    }

    public void setSickTwoWeak(int sickTwoWeak) {
        this.sickTwoWeak = sickTwoWeak;
    }

    public int getSickThreeWeak() {
        return sickThreeWeak;
    }

    public void setSickThreeWeak(int sickThreeWeak) {
        this.sickThreeWeak = sickThreeWeak;
    }

    public int getVaccinatedOneWeak() {
        return vaccinatedOneWeak;
    }

    public void setVaccinatedOneWeak(int vaccinatedOneWeak) {
        this.vaccinatedOneWeak = vaccinatedOneWeak;
    }

    public int getVaccinatedTwoWeak() {
        return vaccinatedTwoWeak;
    }

    public void setVaccinatedTwoWeak(int vaccinatedTwoWeak) {
        this.vaccinatedTwoWeak = vaccinatedTwoWeak;
    }

    public int getVaccinatedThreeWeak() {
        return vaccinatedThreeWeak;
    }

    public void setVaccinatedThreeWeak(int vaccinatedThreeWeak) {
        this.vaccinatedThreeWeak = vaccinatedThreeWeak;
    }

    public int getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(int vaccinated) {
        this.vaccinated = vaccinated;
    }

    public void setPopulationWithCiteSize(int population) {
        if(population < 1000)
            citySize = CitySize.TOWN;
        else if(population < 500000)
            citySize = CitySize.CITY;
        else
            citySize = CitySize.MEGAPOLIS;
        this.population = population;
    }

    public void setCityTransportLinks(float cityTransportLinks) {
        this.cityTransportLinks = cityTransportLinks;
    }

    public void setIntraCityTransportLinks(float intraCityTransportLinks) {
        this.intraCityTransportLinks = intraCityTransportLinks;
    }

    public City(int population,
                float cityTransportLinks,
                float intraCityTransportLinks) {
        if(population < 1000)
            citySize = CitySize.TOWN;
        else if(population < 500000)
            citySize = CitySize.CITY;
        else
            citySize = CitySize.MEGAPOLIS;

        this.population = population;
        this.cityTransportLinks = cityTransportLinks;
        this.intraCityTransportLinks = intraCityTransportLinks;
        this.sickOneWeak = 0;
        this.sickTwoWeak = 0;
        this.sickThreeWeak = 0;
        this.vaccinatedOneWeak = 0;
        this.vaccinatedTwoWeak = 0;
        this.vaccinatedThreeWeak = 0;
        this.vaccinated = 0;
    }

    public CitySize getCitySize() {
        return citySize;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public float getCityTransportLinks() {
        return cityTransportLinks;
    }

    public float getIntraCityTransportLinks() {
        return intraCityTransportLinks;
    }

    public void vaccinate(int n) throws Exception {
        if(population - sickOneWeak - sickTwoWeak - sickThreeWeak - vaccinated - vaccinatedOneWeak - vaccinatedTwoWeak - vaccinatedThreeWeak < n)
            throw new Exception("Cant vaccinate to much people");

        vaccinatedOneWeak += n;
    }

    @Override
    public String toString() {
        return citySize + " population: " + population + " city transport links: " + cityTransportLinks + " intra city transport links: " + intraCityTransportLinks + "\n" +
                " sick: " + (sickOneWeak + sickTwoWeak + sickThreeWeak) + " vaccinated: " + (vaccinated + vaccinatedOneWeak + vaccinatedTwoWeak + vaccinatedThreeWeak);
    }

    public String toCSVString(String CSV_SEPARATOR){
        return population +
                CSV_SEPARATOR +
                cityTransportLinks +
                CSV_SEPARATOR +
                intraCityTransportLinks +
                CSV_SEPARATOR +
                sickOneWeak +
                CSV_SEPARATOR +
                sickTwoWeak +
                CSV_SEPARATOR +
                sickThreeWeak +
                CSV_SEPARATOR +
                vaccinatedOneWeak +
                CSV_SEPARATOR +
                vaccinatedTwoWeak +
                CSV_SEPARATOR +
                vaccinatedThreeWeak +
                CSV_SEPARATOR +
                vaccinated +
                CSV_SEPARATOR;
    }
}


