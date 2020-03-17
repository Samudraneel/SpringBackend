package com.assignment.REST.api;

import com.assignment.REST.service.JukeboxService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class JukeController {

    @Autowired
    private JukeboxService jukeboxService;

    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }

    @RequestMapping(value = "/{settingsID}/", method = RequestMethod.GET)
    public JSONArray findBySettingId(@PathVariable("settingsID") String settingsID) {
        return jukeboxService.getJukeboxesBySettingsID(settingsID);
    }

    @RequestMapping(value = "/{settingsID}/{model}", method = RequestMethod.GET)
    public JSONArray findbySettingIdAndModel(@PathVariable("settingsID") String settingsID,
                                             @PathVariable(value = "model", required = false) String model) {
        return jukeboxService.getJukeboxesBySettingsIDAndModel(settingsID, model);
    }

    @RequestMapping(value = "/{settingsID}/{offset}/{limit}", method = RequestMethod.GET)
    public JSONArray findBySettingId(@PathVariable("settingsID") String settingsID,
                                     @PathVariable(value = "offset", required = false) int offset,
                                     @PathVariable(value = "limit", required = false) int limit) {
//        System.out.println(offset);
//        System.out.println(limit);


        return jukeboxService.getJukeboxesbySettingsIDWithOffsetAndLimit(settingsID, offset, limit);
    }

    @RequestMapping(value = "/{settingsID}/{model}/{offset}/{limit}", method = RequestMethod.GET)
    public JSONArray findBySettingId(@PathVariable("settingsID") String settingsID,
                                     @PathVariable(value = "model", required = false) String model,
                                     @PathVariable(value = "offset", required = false) int offset,
                                     @PathVariable(value = "limit", required = false) int limit) {
        // check if offset and limit are ints. if not, change them to ints. check if valid num.
//        System.out.println(model);
//        System.out.println(offset);
//        System.out.println(limit);

        return jukeboxService.getJukeboxesBySettingsIDModelOffsetAndLimit(settingsID, model, offset, limit);
    }
}
