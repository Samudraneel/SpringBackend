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

    /**
     *
     * @throws ParseException, caused by json parsing.
     */
    private void Parser() throws ParseException {
        Object parsedObject = new JSONParser().parse(this._jukebox);
        JSONArray jukeArray = (JSONArray) parsedObject;

        for(int i = 0; i < jukeArray.size(); i++) {
            JSONObject obj = (JSONObject) jukeArray.get(i);
            this._jukeboxDB.put((String) obj.get("id"), (JSONObject) jukeArray.get(i));
        }
    }

    /**
     *
     * @param requirements, array of requirements the jukebox must satisfy.
     * @param model, model of the jukebox requested for.
     * @param offset, index of the first page will be starting from.
     * @param limit, size of the page.
     * @return: all jukeboxes which satisfy the above.
     */
    public JSONArray GetJukesByRequirementsAndModel(JSONArray requirements, String model, int offset, int limit) {
        Iterator it = this._jukeboxDB.entrySet().iterator();
        JSONArray arr = new JSONArray();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            if (SearchForRequirementsAndModelInComponents(pair, requirements, model)) {
                arr.add(pair.getValue());
            }
        }

        if(offset > 0) {
            return PaginateJukes(arr, offset, limit);
        }

        return arr;
    }

    /**
     *
     * @param keyValuePair, key value pair of the jukebox object.
     * @param requirements, requirements the jukebox needs to satisfy.
     * @param model, model of the jukebox requested for.
     * @return: returns all jukeboxes that satisfy the above.
     */
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

    /**
     *
     * @param requirements,, requirements the jukebox must satisfy.
     * @param offset, index of the first page will be starting from.
     * @param limit, size of the page.
     * @return: an array of jukes that satisfy the requirements identified above.
     */
    public JSONArray GetJukesByRequirements(JSONArray requirements, int offset, int limit) {
        if (limit < 0) {
            limit = 10;
        }
        Iterator it = this._jukeboxDB.entrySet().iterator();
        JSONArray arr = new JSONArray();
        JSONArray jukes = new JSONArray();

        // page is indexed from 1
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            if (SearchForRequirementsAndModelInComponents(pair, requirements, null)) {
                arr.add(pair.getValue());
            }
        }

        if(offset > 0) {
            return PaginateJukes(arr, offset, limit);
        }

        return jukes;
    }

    /**
     *
     * @param arr, array that will be paginated.
     * @param offset, index of the first will be starting from.
     * @param limit, size of the page.
     * @return: a paginated array containing jukes.
     */
    private JSONArray PaginateJukes(JSONArray arr, int offset, int limit) {
        // offset is the index at which the page starts. Thus, with an offset of 2, the 2*limit item on will be shown
        int start = offset * limit;
        int page = 1;
        int counter = 0;
        int j = 0;
        JSONArray temparr = new JSONArray();
        JSONObject obj = new JSONObject();
        JSONArray jukes = new JSONArray();

        while (counter < arr.size()) {
            if (j == limit) {
                j = 0;
                if (start == 0) {
                    obj.put("page" + page, temparr);
                    jukes.add(obj);
                } else {
                    start -= limit;
                    page--;
                }
                obj = new JSONObject();
                temparr = new JSONArray();
                page++;
            }
            temparr.add(arr.get(counter));
            counter++;
            j++;
        }

        if (temparr.size() > 0) {
            obj.put("page" + page, temparr);
            jukes.add(obj);
        }
        return jukes;
    }

    /**
     *
     * @param components: an array containing other components in key value pairs.
     * @return: an array which returns only the values of the component array passed in.
     */
    private HashSet<Object> GetComponentsArray(JSONArray components) {
        HashSet<Object> set = new HashSet<Object>();

        for(int i = 0; i < components.size(); i++) {
            JSONObject obj = (JSONObject) components.get(i);
            set.add((String) obj.get("name"));
        }
        return set;
    }
}

