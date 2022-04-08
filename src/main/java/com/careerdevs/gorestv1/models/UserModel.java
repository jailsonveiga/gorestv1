package com.careerdevs.gorestv1.models;

/*
{
    "id": 230,
    "name": "Bhima Prajapat",
    "email": "bhima_prajapat@schaden-nienow.net",
    "gender": "male",
    "status": "active"
}
*/

public class UserModel {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;

    //Do Not Delete Or Cgange
    public UserModel() {

    }

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String generateReport() {
        return name + " is currently " + status + ". You can contact them at: " + email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
