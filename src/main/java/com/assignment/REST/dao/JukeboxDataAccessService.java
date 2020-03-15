package com.assignment.REST.dao;

import com.assignment.REST.DBCall;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Repository
public class JukeboxDataAccessService {
    public static HashMap<String, JSONObject> _jukeboxDB = new HashMap<String, JSONObject>();
    private String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
    private String _jukebox;

    public JukeboxDataAccessService() throws IOException, ParseException {
        this._jukebox = new DBCall(this.url, "GET").result;
        Parser();
    }

    private void Parser() throws ParseException {
        Object parsedObject = new JSONParser().parse(this._jukebox);
        JSONArray jukeArray = (JSONArray) parsedObject;

        for(int i = 0; i < jukeArray.size(); i++) {
            JSONObject obj = (JSONObject) jukeArray.get(i);
            this._jukeboxDB.put((String) obj.get("id"), (JSONObject) jukeArray.get(i));
        }
    }

    public JSONArray GetJukesByRequirementsAndModel(JSONArray requirements, String model) {
        Iterator it = this._jukeboxDB.entrySet().iterator();
        JSONArray arr = new JSONArray();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            if (SearchForRequirementsAndModelInComponents(pair, requirements, model)) {
                arr.add(pair.getValue());
            }
        }

        return arr;
    }

    public boolean SearchForRequirementsAndModelInComponents(Map.Entry keyValuePair, JSONArray requirements, String model) {
        JSONObject jukebox = (JSONObject) keyValuePair.getValue();

        HashSet<Object> components = GetComponentsArray((JSONArray) jukebox.get("components"));

        for(Object value: requirements) {
            if(!components.contains(value)) {
                return false;
            }
        }

        if(jukebox.get("model") == model && model != null) {
            return true;
        }

        return true;
    }

    public JSONArray GetJukesByRequirements(JSONArray requirements, int offset, int limit) {
        Iterator it = this._jukeboxDB.entrySet().iterator();
        JSONArray arr = new JSONArray();

        // page is indexed from 1
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            if (SearchForRequirementsAndModelInComponents(pair, requirements, null)) {
                arr.add(pair.getValue());
            }
        }

        // suppose offset = 2, limit = 2

        return arr;
    }

    private HashSet<Object> GetComponentsArray(JSONArray components) {
        HashSet<Object> set = new HashSet<Object>();

        for(int i = 0; i < components.size(); i++) {
            JSONObject obj = (JSONObject) components.get(i);
            set.add((String) obj.get("name"));
        }
        return set;
    }
}

