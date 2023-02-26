package com.example.payment.controller;

import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/createPayment")
    public ResponseEntity createPayment(
            @RequestParam(name="paymentType") String paymentType,
            @RequestParam(name="amount") Double amount,
            @RequestParam(name="currency") String currency,
            ModelMap model) throws IOException, JSONException, URISyntaxException {


        ResponseEntity<String> result = paymentService.process(paymentType, amount, currency);
        if (result.getStatusCode().value() == 200){
            JSONObject json = new JSONObject(result.getBody());
            JSONObject res = (JSONObject) json.get("result");
            String url = (String) res.get("redirectUrl");
            URI uri = new URI(url);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } else {
            //URI uri = new URI("pay");
            //HttpHeaders httpHeaders = new HttpHeaders();
            //httpHeaders.setLocation(uri);
            return new ResponseEntity<>(result.getBody(), result.getStatusCode());
        }
    }

}
