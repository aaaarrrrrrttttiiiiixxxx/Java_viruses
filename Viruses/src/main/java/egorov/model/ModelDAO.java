package egorov.model;

import egorov.build.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class ModelDAO {
    private final String PATH = "src\\main\\resources\\Models";
    private static final String CSV_SEPARATOR = ",";

    private int createNextCSV(){
        try {
        int counter = 0;
        File newFile = new File(PATH + "\\" + counter + ".csv");
        while (newFile.exists()) {
            counter++;
            newFile = new File(PATH + "\\" + counter + ".csv");
        }
            newFile.createNewFile();
        return counter;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeModelToCSV(Model model, String file){
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(model.toCSVString(CSV_SEPARATOR));
            writer.close();
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public String save(Model model){
        int i = createNextCSV();
        String s = PATH + "\\" + i + ".csv";
        writeModelToCSV(model, s);
        return i + ".csv";
    }

    public Model getModel(int i) throws Exception {
        List<String> readFile = readFile(PATH + "\\" + i + ".csv");
        List<City> cities = new ArrayList<>();
        Model res = parseModel(readFile.get(0));
        for (int j = 1; j < readFile.size(); j++) {
            cities.add(parseCity(readFile.get(j)));
        }
        res.setCities(cities);
        return res;
    }

    public List<String> readFile(String filename){
            List<String> res = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public Model parseModel(String s) throws Exception {
        try {
            String[] tokens = s.split(CSV_SEPARATOR);
            long money = Long.parseLong(tokens[0]);
            int vaccinationCost = Integer.parseInt(tokens[1]);
            int modelingPeriod = Integer.parseInt(tokens[2]);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss ZZZ yyyy", Locale.ENGLISH);
            Date startDate = formatter.parse(tokens[3]);
            Date currentDate = formatter.parse(tokens[4]);

            return new Model(money, vaccinationCost, new ArrayList<>(), modelingPeriod, startDate, currentDate);
        }
        catch (ParseException e){
            throw new Exception("Invalid csv file");
        }
    }

    public City parseCity(String s) {
        String[] tokens = s.split(CSV_SEPARATOR);

        int population = Integer.parseInt(tokens[0]);
        final float cityTransportLinks = Float.parseFloat(tokens[1]);
        final float intraCityTransportLinks = Float.parseFloat(tokens[2]);
        int sickOneWeak = Integer.parseInt(tokens[3]);
        int sickTwoWeak = Integer.parseInt(tokens[4]);
        int sickThreeWeak = Integer.parseInt(tokens[5]);
        int vaccinatedOneWeak = Integer.parseInt(tokens[6]);
        int vaccinatedTwoWeak = Integer.parseInt(tokens[7]);
        int vaccinatedThreeWeak = Integer.parseInt(tokens[8]);
        int vaccinated = Integer.parseInt(tokens[9]);

        return new City(population,
                cityTransportLinks,
                intraCityTransportLinks,
                sickOneWeak,
                sickTwoWeak,
                sickThreeWeak,
                vaccinatedOneWeak,
                vaccinatedTwoWeak,
                vaccinatedThreeWeak,
                vaccinated);
    }
}
