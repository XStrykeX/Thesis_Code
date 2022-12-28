package dk.ccm_ee_dragosandreipetrut.thesisapplicationcorrected;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText RegCode;
    private EditText RegUsername;
    private EditText RegPassword;
    private Button RegButton;
    private Button Back2Login;

    public Users credentials;

    SharedPreferences Storage;
    SharedPreferences.Editor EditStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        RegCode = findViewById(R.id.registerCode);
        RegUsername = findViewById(R.id.registerUsername);
        RegPassword = findViewById(R.id.registerPassword);
        RegButton = findViewById(R.id.registerButton);
        Back2Login = findViewById(R.id.back2LoginButton);

        Storage = getApplicationContext().getSharedPreferences("Credentials", MODE_PRIVATE);
        EditStorage = Storage.edit();

        credentials = new Users();

        if (Storage != null){

            //get the credentials from the stored file
            Map<String,String> registerMap = (Map<String, String>) Storage.getAll();

            if (registerMap.size() != 0){
                credentials.load_login_data(registerMap);
            }

            //String Registered_Username = Storage.getString("Username","");
            //String Registered_Password = Storage.getString("Password","");
        }

        Back2Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_2_login = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(back_2_login);
            }
        });

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NewRegUsername = RegUsername.getText().toString();
                String NewRegPassword = RegPassword.getText().toString();
                String CheckRegCode = RegCode.getText().toString();

                if(checkRegistration(NewRegUsername, NewRegPassword, CheckRegCode)){

                    if(credentials.username_taken(NewRegUsername)){
                        Toast.makeText(getApplicationContext(),"Username already taken.",Toast.LENGTH_SHORT).show();
                    }else{
                        credentials.add_login_data(NewRegUsername, NewRegPassword);
                        EditStorage.putString(NewRegUsername, NewRegPassword);

                        //Commits changes to the file
                        EditStorage.apply();

                        Intent registration_successful = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(registration_successful);
                    }
                }
            }
        });
    }

    private boolean checkRegistration(String username, String password, String code){
        if (!code.equals("Reg1234")) {
            Toast.makeText(this,"Incorrect registration code!",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(username.isEmpty() || password.length()<10){
                Toast.makeText(this,"Insufficient information or password under 10 characters!",Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }

    }

}