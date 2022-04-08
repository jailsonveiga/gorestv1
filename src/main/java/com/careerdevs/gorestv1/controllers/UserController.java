package com.careerdevs.gorestv1.controllers;

import com.careerdevs.gorestv1.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
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

            var user = restTemplate.getForObject(url, UserModel.class);

            assert user != null;
            System.out.println("Report: \n" + user.generateReport());

            return user;

//            return restTemplate.getForObject(url, UserModel.class);

        } catch (HttpClientErrorException.NotFound exception) {

            return "User could not be found, user #" + userId + " does not exist";

        } catch (Exception exception) {
            System.out.println(exception.getClass());
            return exception.getMessage();
        }
    }

    //(URL / endpoint) DELETE http://localhost:4444/api/user/{id}
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

        } catch (HttpClientErrorException.Unauthorized exception) {

            return "You are not authorized to delete user #" + userId;

        } catch (Exception exception) {
            System.out.println(exception.getClass());
            return exception.getMessage();
        }
    }

    @PostMapping("/")
    public Object postUserQueryParam(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status,
            RestTemplate restTemplate
    ) {

        try {

            String url = "https://gorest.co.in/public/v2/users";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;

            UserModel newUser = new UserModel(name, email, gender, status);

            //TODO: validate that gender and status are valid before continuing

            //TODO: Validate that the email is a valid email

            System.out.println("Data to be sent:\n" + newUser);

            HttpEntity<UserModel> request = new HttpEntity<>(newUser);

            return restTemplate.postForEntity(url, request, UserModel.class);

        } catch (Exception exception) {

            System.out.println(exception.getClass());
            return exception.getMessage();

        }
    }

    @PostMapping("/")
    public ResponseEntity postUser(
            RestTemplate restTemplate,
            @RequestBody UserModel newUser
    ) {
        try {
            String url = "https://gorest.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;

            HttpEntity<UserModel> request = new HttpEntity<>(newUser);

            return restTemplate.postForEntity(url, request, UserModel.class);
        } catch (Exception e) {
            System.out.println(e.getClass() + " \n " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
