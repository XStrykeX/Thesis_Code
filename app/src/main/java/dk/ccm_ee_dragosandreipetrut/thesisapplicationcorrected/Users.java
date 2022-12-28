package dk.ccm_ee_dragosandreipetrut.thesisapplicationcorrected;

import java.util.HashMap;
import java.util.Map;

public class Users {

    HashMap<String, String> login_data = new HashMap<String, String>();

    public void add_login_data(String add_username, String add_password){
        login_data.put(add_username, add_password);
    }

    public boolean username_taken(String add_username){
        return login_data.containsKey(add_username);
    }

    public boolean verify_login(String add_username, String add_password){
        if(login_data.containsKey(add_username)){
            if(add_password.equals(login_data.get(add_username))){
                return true;}
        }
        return false;}

    public void load_login_data (Map<String, String> load_map){
        for (Map.Entry<String, String> entries:load_map.entrySet()){
            login_data.put(entries.getKey(), entries.getValue());
        }
    }

}
