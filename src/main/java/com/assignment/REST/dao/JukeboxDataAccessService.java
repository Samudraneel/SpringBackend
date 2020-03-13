package com.assignment.REST.dao;

import com.assignment.REST.DBCall;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;

@Repository
public class JukeboxDataAccessService {
    public static HashMap<String, JSONObject> _jukeboxDB = new HashMap<String, JSONObject>();
    private String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
    private String _jukebox;

    public JukeboxDataAccessService() throws IOException, ParseException {
        this._jukebox = new DBCall(this.url, "GET").result;
        this.Parser();
    }

    private void Parser() throws ParseException {
        Object parsedObject = new JSONParser().parse(this._jukebox);
        JSONArray jukeArray = (JSONArray) parsedObject;

        for(int i = 0; i < jukeArray.size(); i++) {
            JSONObject obj = (JSONObject) jukeArray.get(i);
            this._jukeboxDB.put((String) obj.get("id"), (JSONObject) jukeArray.get(i));
        }
    }

    public String CallMe() {
        return "WORKING POG";
    }
}

