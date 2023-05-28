package egorov.controller;

import egorov.build.Autowired;
import egorov.build.Component;
import egorov.model.City;
import egorov.model.Model;
import egorov.service.PathParamService;
import egorov.service.Service;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Controller extends  HttpServlet {
    @Autowired
    private Service service;
    @Autowired
    private PathParamService pathParser;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Model model = new Model();
            String path = request.getQueryString();
            if (path != null && !path.isEmpty())
                model = service.getModel(pathParser.getParams(path).get("id"));
            else
                response.sendError(400);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(model));
        } catch (Exception e) {
            response.sendError(500);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = getJson(request);
            service.create(mapper.readValue(json, Model.class));

            response.setStatus(200);
        } catch (Exception e) {
            response.sendError(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = getJson(request);
            service.update(mapper.readValue(json, Model.class));
            response.setStatus(200);
        } catch (Exception e) {
            response.sendError(400);
        }
    }
    private String getJson(HttpServletRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        var reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    private Model parseJson(String json) throws IOException {
        Model model = mapper.readValue(json, Model.class);
        String cityes = json.substring(json.indexOf("\"cities\": ["), json.indexOf("]"));

        int ind = cityes.indexOf("{");
        List<City> cityList = new ArrayList<>();
        while (ind > 0){
            int ind2 = cityes.indexOf("}");
            City city = mapper.readValue(cityes.substring(ind, ind2), City.class);
            cityList.add(city);
            json = cityes.substring(ind2);
            ind = cityes.indexOf("{");
        }
        model.setCities(cityList);
        return model;
    }
}
