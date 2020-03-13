package com.assignment.REST;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// Note that ideally the data should exist in a database of sorts
// I considered putting the data into a local sql instance after I call the endpoint
// Decided to keep a static reference after calling this class
public class DBCall {
    public String result = "";
    private URL url;
    private String requestType;

    public DBCall(String endpoint, String requestType) throws IOException {
        this.url = new URL(endpoint);
        this.requestType = requestType;
        this.ExecuteCall();
    }

    private void ExecuteCall() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(this.requestType);
        con.setRequestProperty("Content-Type", "application/json");

        con.connect();

        int responseCode = con.getResponseCode();

        if (responseCode != 200) {
            // do something
            System.out.println("CALL FAILED, ERROR!");
        } else {
            Scanner sc = new Scanner(url.openStream());

            while (sc.hasNext()) {
                this.result += sc.nextLine();
            }

            sc.close();
        }
    }
}
