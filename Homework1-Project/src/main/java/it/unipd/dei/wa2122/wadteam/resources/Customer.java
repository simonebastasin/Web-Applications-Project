package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Customer implements Resource {

    final int id;
    final String name;
    final String surname;
    final String fiscal_code;
    final String address;
    final String email;
    final String phone_number;
    final String username;
    final String password;

    public Customer(int id,String name,String surname,String fiscal_code,String address,String email, String  phone_number,String username,String password) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.fiscal_code= fiscal_code;
        this.address=address;
        this.email=email;
        this.phone_number=phone_number;
        this.username = username;
        this.password = password;
    }

    public final int getId(){return id;}
    public final String getName(){return name;}
    public final String getSurname() {return surname;}
    public final String getFiscal_code(){return fiscal_code;}
    public final String getAddress(){return address;}
    public final String getEmail() {return email;}
    public final String getPhone_number(){return phone_number;}
    public final String getUsername(){return username;}
    public final String getPassword(){return password;}

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("surname", surname);
        jsonObject.put("fiscal_code", fiscal_code);
        jsonObject.put("address", address);
        jsonObject.put("email", email);
        jsonObject.put("phone_number", phone_number);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        return jsonObject;
    }
    public static Customer fromJson(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        Integer name = jsonObject.getString("name");
        String surname = jsonObject.getString("surname");
        String fiscal_code = jsonObject.getString("fiscal_code");
        String address = jsonObject.getString("address");
        String email=jsonObject.getString("email");
        String phone_number=jsonObject.getString("phone_number");
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        return new Customer(id,name,surname,fiscal_code,address,email,phone_number,username,password);
    }
}
