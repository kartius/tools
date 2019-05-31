package com.example.springsocial.controller;


import com.example.springsocial.config.GoogleConfig;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth/sso")
public class TestSSOController {

    @Autowired
    private GoogleConfig googleConfig;

    String clientId;
    String clientSecret;

    // check if token is active https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=access_token

    // remove token -  https://accounts.google.com/o/oauth2/revoke?token=access_token

    //logout  https://accounts.google.com/logout

    HttpClient client = new HttpClient();

    @GetMapping("/login/google")
    public ResponseEntity<?> authenticateUser() throws URISyntaxException {
        clientId = googleConfig.getClientId();
        clientSecret = googleConfig.getClientSecret();
        String redirectUri = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email&state=%2Fprofile&response_type=code&client_id=" + clientId + "&redirect_uri=http://localhost:8080/auth/sso/login";
        URI uri = new URI(redirectUri);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest httpRequest) throws IOException {
        httpRequest.getHeaderNames();
        httpRequest.getParameterNames();
        String code = httpRequest.getParameter("code");
        String foros = "code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + "http://localhost:8080/auth/sso/login" +
                "&grant_type=authorization_code";
        String accessToken = getAccessToken(foros);
        System.out.println(accessToken);
        String userInfo = getUserInfo(accessToken);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/notification")
    public ResponseEntity<String> notification(HttpServletRequest httpRequest) {
        System.out.println(httpRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getAccessToken(String foros) throws IOException {
        String url = "https://accounts.google.com/o/oauth2/token";

        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        try {
            post.setRequestEntity(new StringRequestEntity(foros, null, null));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String accessToken = null;
        try {
            client.executeMethod(post);
            String resp = post.getResponseBodyAsString();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(resp);
            JSONObject parsed = (JSONObject) obj;
            accessToken = (String) parsed.get("access_token");
        } catch (HttpException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    private String getUserInfo(String accessToken) {
        GetMethod getUserInfo = new GetMethod("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);
        String googleId = null;
        String email = null;
        String name = null;
        String firstName = null;
        String lastName = null;
        try {
            client.executeMethod(getUserInfo);
            String resp = getUserInfo.getResponseBodyAsString();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(resp);
            JSONObject parsed = (JSONObject) obj;
            googleId = (String) parsed.get("id");
            email = (String) parsed.get("email");
            name = (String) parsed.get("name");
            firstName = (String) parsed.get("given_name");
            lastName = (String) parsed.get("family_name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return email + "\n" + name + "\n" + firstName + "\n" + lastName;
    }


}
