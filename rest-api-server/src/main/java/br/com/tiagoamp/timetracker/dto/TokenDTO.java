package br.com.tiagoamp.timetracker.dto;

public class TokenDTO {

    private String token;

    private String type = "Bearer";


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
