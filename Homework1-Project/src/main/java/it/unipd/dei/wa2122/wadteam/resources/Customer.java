package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

public class Customer implements Resource {

    final Integer id;
    final String name;
    final String surname;
    final String fiscalCode;
    final String address;
    final String email;
    final String phoneNumber;
    final String username;
    final String password;

    public Customer(Integer id,String name,String surname,String fiscalCode,String address,String email, String  phoneNumber,String username,String password) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.fiscalCode= fiscalCode;
        this.address=address;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.username = username;
        this.password = password;
    }

    public final int getId(){return id;}
    public final String getName(){return name;}
    public final String getSurname() {return surname;}
    public final String getFiscalCode(){return fiscalCode;}
    public final String getAddress(){return address;}
    public final String getEmail() {return email;}
    public final String getPhoneNumber(){return phoneNumber;}
    public final String getUsername(){return username;}
    public final String getPassword(){return password;}

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("surname", surname);
        jsonObject.put("fiscalCode", fiscalCode);
        if(address!=null)
            jsonObject.put("address", address);
        jsonObject.put("email", email);
        if(phoneNumber!=null)
            jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        return jsonObject;
    }
    public static Customer fromJSON(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String surname = jsonObject.getString("surname");
        String fiscalCode = jsonObject.getString("fiscalCode");
        String address=null;
        if(jsonObject.has("address"))
         address = jsonObject.getString("address");
        String email=jsonObject.getString("email");
        String phoneNumber=null;
        if(jsonObject.has("phoneNumber"))
             phoneNumber=jsonObject.getString("phoneNumber");
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        return new Customer(id,name,surname,fiscalCode,address,email,phoneNumber,username,password);
    }
}
