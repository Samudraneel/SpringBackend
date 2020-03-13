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

    public String getJukeboxesBySettingsID(String SettingsID) {
        // GET REQUIREMENTS BY SETTINGS ID
//        String SettingsID = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
        JSONArray arr = settingsDao.GetRequirementsBySettingsID(SettingsID);
        // GET ALL JUKEBOXES IF THEY HAVE THE REQUIREMENTS

        return jukeboxDao.CallMe();
    }
}
