package egorov.model;

import egorov.build.Autowired;
import egorov.build.Component;

import java.sql.*;

@Component
public class ModelSQLDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/viruses";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;
    @Autowired
    private CitySQLDAO citySQLDAO;

    public ModelSQLDAO() {
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

    public Model getModel(int id){
        Model model = new Model();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM model WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            model.setId(resultSet.getInt("id"));
            model.setMoney(resultSet.getInt("money"));
            model.setVaccinationCost(resultSet.getInt("vaccination_cost"));
            model.setModelingPeriod(resultSet.getInt("modeling_period"));
            model.setStartDate(resultSet.getDate("start_date"));
            model.setCurrentDate(resultSet.getDate("curr_date"));

            model.setCities(citySQLDAO.getCitiesForModel(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

    public void addNewModel(Model model){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            INSERT INTO public.model(id,
                                                    season,
                                                    money,
                                                    vaccination_cost,
                                                    modeling_period,
                                                    start_date,
                                                    curr_date)
                            VALUES (nextval('model_id_seq'), ?, ?, ?, ?, ?, ?);""");

            setStatement(model, preparedStatement);
            for (City c: model.getCities()) {
                citySQLDAO.addNewCity(c);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateModel(Model model){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE public.model SET
                    season=?,
                    money=?,
                    vaccination_cost=?,
                    modeling_period=?,
                    start_date=?,
                    curr_date=? WHERE id=?;""");
            setStatement(model, preparedStatement);
            preparedStatement.setInt(7, model.getId());
            preparedStatement.executeUpdate();
            for (City c: model.getCities()) {
                citySQLDAO.updateCity(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStatement(Model model, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, model.getSeason(), Types.OTHER);
        preparedStatement.setLong(2, model.getMoney());
        preparedStatement.setInt(3, model.getVaccinationCost());
        preparedStatement.setInt(4, model.getModelingPeriod());
        preparedStatement.setDate(5, new Date(model.getStartDate().getTime()));
        preparedStatement.setDate(6, new Date(model.getCurrentDate().getTime()));
    }
}
