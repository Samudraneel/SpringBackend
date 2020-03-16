package com.assignment.REST.dao;

import com.assignment.REST.DBCall;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;

@Repository
public class SettingsDataAccessService {
    private static HashMap<String, JSONArray> _settingsDB = new HashMap<String, JSONArray>();
    // Ideally the url should be hidden somewhere else, like the db maybe.
    private String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings";
    private String _settings;

    /**
     *
     * @throws IOException, caused by the "DBCall" method which is actually a call to an endpoint in this case.
     * @throws ParseException, caused by json parsing.
     * None of these exceptions will actually be hit in this assignment.
     */
    public SettingsDataAccessService() throws IOException, ParseException {
        this._settings = new DBCall(this.url, "GET").result;
        this.Parser();
    }

    /**
     *
     * @throws ParseException, caused by json parsing.
     */
    private void Parser() throws ParseException {
        Object parsedObject = new JSONParser().parse(this._settings);
        JSONObject bigJSONObject = (JSONObject) parsedObject;
        JSONArray settingsArray =  (JSONArray) bigJSONObject.get("settings");

        for(int i = 0; i < settingsArray.size(); i++) {
            JSONObject obj = (JSONObject) settingsArray.get(i);
            this._settingsDB.put((String) obj.get("id"), (JSONArray) obj.get("requires"));
        }
    }

    /**
     *
     * @param id, settingsId.
     * @return an array of requirements based on the id provided.
     */
    public JSONArray GetRequirementsBySettingsID(String id) {
        return this._settingsDB.get(id);
    }
}
