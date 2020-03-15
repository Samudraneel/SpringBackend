package com.assignment.REST.service;

import com.assignment.REST.dao.JukeboxDataAccessService;
import com.assignment.REST.dao.SettingsDataAccessService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JukeboxService {

    @Autowired
    private JukeboxDataAccessService jukeboxDao;
    @Autowired
    private SettingsDataAccessService settingsDao;

    public JSONArray getJukeboxesBySettingsID(String settingsID) {
//        String SettingsID = "b43f247a-8478-4f24-8e28-792fcfe539f5";
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        return jukeboxDao.GetJukesByRequirements(requirements, -1, -1);
    }

    public JSONArray getJukeboxesBySettingsIDAndModel(String settingsID, String model) {
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        return jukeboxDao.GetJukesByRequirementsAndModel(requirements, model);
    }

    public JSONArray getJukeboxesbySettingsIDWithOffsetAndLimit(String settingsID, int offset, int limit) {
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        JSONArray jukes = jukeboxDao.GetJukesByRequirements(requirements, offset, limit);

        return new JSONArray();
    }
}
