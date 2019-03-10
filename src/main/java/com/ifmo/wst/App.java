package com.ifmo.wst;//package com.wishmaster.ifmo.ws.jaxws;

import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8080/PersonService";
        Endpoint.publish(url, new PersonWebService());
    }
}