package egorov.service;

import egorov.build.Autowired;
import egorov.build.Component;
import egorov.model.Model;
import egorov.model.ModelSQLDAO;

@Component
public class Service {

    @Autowired
    private ModelSQLDAO modelDao;

    public Model getModel(int id) {
        return modelDao.getModel(id);
    }

    public void create(Model model) {
        modelDao.addNewModel(model);
    }

    public void update(Model model) {
        modelDao.updateModel(model);
    }

}
