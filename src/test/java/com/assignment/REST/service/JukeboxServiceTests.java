package com.assignment.REST.service;

import org.json.simple.JSONArray;

import static junit.framework.TestCase.assertEquals;

public class JukeboxServiceTests {
    private String settingsID = "b43f247a-8478-4f24-8e28-792fcfe539f5";
    private JSONArray res = new JSONArray();

    public void getJukeboxesBySettingsIDTest() {
        JukeboxService service = new JukeboxService();
        JSONArray arr = service.getJukeboxesBySettingsID(this.settingsID);
//        assertEquals(arr, this.res);
        System.out.println(arr);
        System.out.println(this.res);
    }
}
