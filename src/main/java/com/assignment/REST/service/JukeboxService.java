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

    /**
     *
     * @param settingsID, id which shows the requirements that jukeboxes must have.
     * @return: array of jukeboxes that satisfies the requirements.
     */
    public JSONArray getJukeboxesBySettingsID(String settingsID) {
//        String SettingsID = "b43f247a-8478-4f24-8e28-792fcfe539f5";
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        return jukeboxDao.GetJukesByRequirements(requirements, -1, -1);
    }

    /**
     *
     * @param settingsID, id which shows the requirements that jukeboxes must have.
     * @param model, identifies the jukebox model.
     * @return: an array that contains all jukeboxes satisfying the requirement by the settingsID and model provided.
     */
    public JSONArray getJukeboxesBySettingsIDAndModel(String settingsID, String model) {
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        return jukeboxDao.GetJukesByRequirementsAndModel(requirements, model, -1, -1);
    }

    /**
     *
     * @param settingsID, id which shows the requirements that jukeboxes must have.
     * @param offset, identifies the index where the page starts.
     * @param limit
     * @return
     */
    public JSONArray getJukeboxesbySettingsIDWithOffsetAndLimit(String settingsID, int offset, int limit) {
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        JSONArray jukes = jukeboxDao.GetJukesByRequirements(requirements, offset, limit);

        return jukes;
    }

    /**
     *
     * @param settingsID, id which shows the requirements that jukeboxes must have.
     * @param model, identifies the jukebox model.
     * @param offset, identifies the index where the page starts.
     * @param limit, identifies the limit of each page.
     * @return
     */
    public JSONArray getJukeboxesBySettingsIDModelOffsetAndLimit(String settingsID, String model, int offset, int limit) {
        JSONArray requirements = settingsDao.GetRequirementsBySettingsID(settingsID);
        return jukeboxDao.GetJukesByRequirementsAndModel(requirements, model, offset, limit);
    }
}
