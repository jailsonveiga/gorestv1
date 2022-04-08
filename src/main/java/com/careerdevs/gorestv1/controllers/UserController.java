package com.careerdevs.gorestv1.controllers;

import com.careerdevs.gorestv1.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping ("/api/user")
public class UserController {
    @Autowired
    Environment env;

    //URL / endpoint http://localhost:4444/api/user/token
    @GetMapping("/token")
    public String getToken() {

        return env.getProperty("GOREST_TOKEN");
    }

    //(URL / endpoint) GET http://localhost:4444/api/user/{id}
    @GetMapping("/{id}")
    public Object getOneUser(
            @PathVariable("id") String userId,
            RestTemplate restTemplate
    ) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;
            String apiToken = env.getProperty("GOREST_TOKEN");

           //Manual Approach
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBearerAuth(env.getProperty("GOREST_TOKEN"));
//            HttpEntity request = new HttpEntity(headers);
//
//            return restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    request,
//                    Object.class
//            );

            //return restTemplate.getForObject(url, Object.class);

            //Shortened Approach
            url += "?access-token=" + apiToken;

           var user  = restTemplate.getForObject(url, UserModel.class);

            assert user != null;
            System.out.println("Report: \n" + user.generateReport());

           return user;
//            return restTemplate.getForObject(url, UserModel.class);

        } catch (Exception exception) {
            return "404: No user exist with the ID " + userId;
        }
    }

    //(URL / endpoint) DELETE http://localhost:4444/api/user/token
    @DeleteMapping("/{id}")
    public Object deleteOneUser(
            @PathVariable("id") String userId,
            RestTemplate restTemplate
    ) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + userId;

            String token = env.getProperty("GOREST_TOKEN");
//            HttpHeaders headers = new HttpHeaders();
//
//            headers.setBearerAuth(token);
//
//            HttpEntity request = new HttpEntity(headers);
//
//            restTemplate.exchange(
//                    url,
//                    HttpMethod.DELETE,
//                    request,
//                    Object.class
//            );
            //headers.set("Authorization", "Bearer " + token);

            url += "?access-token=" + token;

            restTemplate.delete(url);

            return "Successfully Deleted user #" + userId;

        } catch (HttpClientErrorException.NotFound exception) {

                return "User could not be delete, user #" + userId + " does not exist";

        }  catch (HttpClientErrorException.Unauthorized exception) {

            return "You are not authorized to delete user #" + userId;

        }  catch  (Exception exception) {
            System.out.println(exception.getClass());
            return exception.getMessage();
        }
    }


}
