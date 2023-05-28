package egorov.model;

import egorov.build.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CitySQLDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/viruses";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;

    public CitySQLDAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> getCitiesForModel(int  modelId){
        List<City> cities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM city WHERE model_id = ?;");
            preparedStatement.setInt(1, modelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("id"));
                city.setPopulationWithCiteSize(resultSet.getInt("population"));
                city.setCityTransportLinks(resultSet.getFloat("city_transport_links"));
                city.setIntraCityTransportLinks(resultSet.getFloat("intra_city_transport_links"));
                city.setSickOneWeak(resultSet.getInt("sick_one_weak"));
                city.setSickTwoWeak(resultSet.getInt("sick_two_weak"));
                city.setSickThreeWeak(resultSet.getInt("sick_three_weak"));
                city.setVaccinatedOneWeak(resultSet.getInt("vaccinated_one_weak"));
                city.setVaccinatedTwoWeak(resultSet.getInt("vaccinated_two_weak"));
                city.setVaccinatedTwoWeak(resultSet.getInt("vaccinated_three_weak"));
                city.setVaccinated(resultSet.getInt("vaccinated"));
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    public void addNewCity(City city){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            INSERT INTO public.city(id,
                                                    city_size,
                                                    population,
                                                    city_transport_links,
                                                    intra_city_transport_links,
                                                    sick_one_weak,
                                                    sick_two_weak,
                                                    sick_three_weak,
                                                    vaccinated_one_weak,
                                                    vaccinated_two_weak,
                                                    vaccinated_three_weak,
                                                    vaccinated,
                                                    model_id)
                            VALUES (nextval('city_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""");

            setStatement(city, preparedStatement);

            PreparedStatement seqStat = connection.prepareStatement("SELECT last_value FROM model_id_seq;");
            ResultSet resultSet = seqStat.executeQuery();
            resultSet.next();
            int nextVal = resultSet.getInt("last_value");
            preparedStatement.setInt(12, nextVal);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateCity(City city){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE public.city SET
                    city_size=?,
                    population=?,
                    city_transport_links=?,
                    intra_city_transport_links=?,
                    sick_one_weak=?,
                    sick_two_weak=?,
                    sick_three_weak=?,
                    vaccinated_one_weak=?,
                    vaccinated_two_weak=?,
                    vaccinated_three_weak=?,
                    vaccinated=? WHERE id=?;""");
            setStatement(city, preparedStatement);
            preparedStatement.setInt(12, city.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStatement(City city, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, city.getCitySize(), Types.OTHER);
        preparedStatement.setInt(2, city.getPopulation());
        preparedStatement.setFloat(3, city.getCityTransportLinks());
        preparedStatement.setFloat(4, city.getIntraCityTransportLinks());
        preparedStatement.setInt(5, city.getVaccinatedOneWeak());
        preparedStatement.setInt(6, city.getSickTwoWeak());
        preparedStatement.setInt(7, city.getSickThreeWeak());
        preparedStatement.setInt(8, city.getVaccinatedOneWeak());
        preparedStatement.setInt(9, city.getVaccinatedTwoWeak());
        preparedStatement.setInt(10, city.getVaccinatedThreeWeak());
        preparedStatement.setInt(11, city.getVaccinated());
    }
}
