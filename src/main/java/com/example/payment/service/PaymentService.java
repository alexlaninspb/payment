package com.example.payment.service;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class PaymentService {

    public ResponseEntity<String> process(String paymentType, double amount, String currency) throws IOException, JSONException {
        //Authorization: Bearer cAmmvalAikARkB81fgxgMtnMbEdNbuWa
        URL url = new URL("https://app-demo.paze.eu/api/v1/payments");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","Bearer " + "cAmmvalAikARkB81fgxgMtnMbEdNbuWa");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        JSONObject json = new JSONObject();
        json.put("paymentType", paymentType);
        json.put("amount", amount);
        json.put("currency", currency);
        //String jsonInputString = json.toString();

        try(OutputStream os = conn.getOutputStream()) {
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }
        //conn.connect();
        int responseCode = conn.getResponseCode();
        StringBuilder response = new StringBuilder();
        if(responseCode == 400 || responseCode == 401) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String responseString = response.toString();
                System.out.println("Response:-" + responseString);
                return new ResponseEntity<>(responseString, HttpStatusCode.valueOf(responseCode));
            }
        } else {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String responseString = response.toString();
                System.out.println("Response:-" + responseString);
                return new ResponseEntity<>(responseString, HttpStatusCode.valueOf(responseCode));
            }
        }
    }

}
