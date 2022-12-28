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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //Declare Variables
    private EditText Username;
    private EditText Password;
    private TextView LoginMsg;
    private Button Login;
    private Button Register;

    private Boolean Correct;

    public Users credentials;

    SharedPreferences Storage;
    SharedPreferences.Editor EditStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Make screen on landscape only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Associate variables with GUI elements
        Username = findViewById(R.id.usernameLogin);
        Password = findViewById(R.id.passwordLogin);
        LoginMsg = findViewById(R.id.loginMsg);
        Login = findViewById(R.id.loginButton);
        Register = findViewById(R.id.loginRegisterButton);

        credentials = new Users();

        Storage = getApplicationContext().getSharedPreferences("Credentials", MODE_PRIVATE);

        if (Storage != null){

            //get the credentials from the stored file
            Map<String,String> loginMap = (Map<String, String>) Storage.getAll();

            if (loginMap.size() != 0){
                credentials.load_login_data(loginMap);
            }

            //String Registered_Username = Storage.getString("Username","");
            //String Registered_Password = Storage.getString("Password","");
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_registration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(new_registration);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_Username = Username.getText().toString();
                String login_Password = Password.getText().toString();

                if(login_Username.isEmpty() || login_Password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Missing username or password. Please type all the details requested.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Correct = login_checkin(login_Username, login_Password);
                    if(!Correct){
                        Toast.makeText(LoginActivity.this,"Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent login_successful = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login_successful);
                    }
                }
            }
        });
    }

    private Boolean login_checkin(String tryUsername, String tryPassword){
        return credentials.verify_login(tryUsername, tryPassword);
    }

}