package com.assignment.REST.api;

import com.assignment.REST.service.JukeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class JukeController {

    @Autowired
    private JukeboxService jukeboxService;

    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }

    @RequestMapping(value = "/{settingsID}", method = RequestMethod.GET)
    public String findBySettingId(@PathVariable("settingsID") String settingsID) {
        return jukeboxService.getJukeboxesBySettingsID(settingsID);
    }
}
