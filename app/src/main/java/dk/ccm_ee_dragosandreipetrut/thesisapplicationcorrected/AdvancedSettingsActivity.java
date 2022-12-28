package dk.ccm_ee_dragosandreipetrut.thesisapplicationcorrected;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdvancedSettingsActivity extends AppCompatActivity {

    Button standard_settings, back2menu, throttle_increase, throttle_decrease, PTO_increase, PTO_decrease, save;
    TextView actual_throttle, actual_PTO, voltage;
    public EditText throttle_max, throttle_min, PTO_max, PTO_min, threshold, code;

    int maxthrt = 100;
    int minthrt = 0;
    int maxpto = 100;
    int minpto = 0;
    public int thresh;
    public int actthrt = 0;
    int actpto = 0;
    int volt = 12;

    SharedPreferences technical_data;
    SharedPreferences.Editor edit_data;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);

        back2menu = findViewById(R.id.buttonBack2Main);
        standard_settings = findViewById(R.id.buttonNormalSettings);
        throttle_increase = findViewById(R.id.motorForward);
        throttle_decrease = findViewById(R.id.motorBackwards);
        PTO_increase = findViewById(R.id.ptoIncrease);
        PTO_decrease = findViewById(R.id.ptoDecrease);
        save = findViewById(R.id.buttonSave);
        actual_throttle = findViewById(R.id.actualThrottle);
        actual_PTO = findViewById(R.id.actualPTO);
        voltage = findViewById(R.id.voltage);
        throttle_max = findViewById(R.id.throttleMax);
        throttle_min = findViewById(R.id.throttleMin);
        PTO_max = findViewById(R.id.ptoMax);
        PTO_min = findViewById(R.id.ptoMin);
        threshold = findViewById(R.id.treshold);
        code = findViewById(R.id.editTextCodeToSave);

        technical_data = getApplicationContext().getSharedPreferences("TechnicalData", MODE_PRIVATE);
        edit_data = technical_data.edit();

        maxthrt = technical_data.getInt("Maximum Throttle", 100);
        minthrt = technical_data.getInt("Minimum Throttle", 0);
        maxpto = technical_data.getInt("Maximum PTO", 100);
        minpto = technical_data.getInt("Minimum PTO", 0);
        thresh = technical_data.getInt("Threshold", 50);

        set_text_value();
        actual_throttle.setText(Integer.toString(actthrt));
        actual_PTO.setText(Integer.toString(actpto));
        voltage.setText(Integer.toString(volt));

        back2menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2menu = new Intent(AdvancedSettingsActivity.this, MainActivity.class);
                startActivity(back2menu);
            }
        });

        standard_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent advanced_settings = new Intent(AdvancedSettingsActivity.this, StandardSettingsActivity.class);
                startActivity(advanced_settings);
            }
        });

        throttle_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actthrt <= 99)
                {
                    actthrt += 1;
                    actual_throttle.setText(Integer.toString(actthrt));
                }
            }
        });

        throttle_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actthrt >= 1)
                {
                    actthrt -= 1;
                    actual_throttle.setText(Integer.toString(actthrt));
                }
            }
        });

        PTO_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actpto <=99)
                {
                    actpto += 1;
                    actual_PTO.setText(Integer.toString(actpto));
                }
            }
        });

        PTO_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actpto >=1)
                {
                    actpto -= 1;
                    actual_PTO.setText(Integer.toString(actpto));
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_text_values();
                String code_string = code.getText().toString();
                if (maxthrt < minthrt || maxpto < minpto){
                    Toast.makeText(AdvancedSettingsActivity.this,"Minimum value cannot exceed maximum value!", Toast.LENGTH_SHORT).show();
                } else {
                    if (maxthrt == minthrt || maxpto == minpto){
                        Toast.makeText(AdvancedSettingsActivity.this,"Maximum value and minimum value cannot be the same!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!code_string.equals("save.changes")){
                            Toast.makeText(AdvancedSettingsActivity.this,"Incorrect code!", Toast.LENGTH_SHORT).show();
                        }else{
                            edit_data.putInt("Threshold", thresh);
                            edit_data.putInt("Maximum Throttle", maxthrt);
                            edit_data.putInt("Minimum Throttle", minthrt);
                            edit_data.putInt("Maximum PTO", maxpto);
                            edit_data.putInt("Minimum PTO", minpto);
                            edit_data.apply();
                            Toast.makeText(AdvancedSettingsActivity.this,"Data has been saved!", Toast.LENGTH_SHORT).show();
                            Intent back2menu = new Intent(AdvancedSettingsActivity.this, MainActivity.class);
                            startActivity(back2menu);
                        }
                    }
                }
            }
        });

    }
    private void get_text_values(){
        String max_throttle = throttle_max.getText().toString();
        String min_throttle = throttle_min.getText().toString();
        String max_PTO = PTO_max.getText().toString();
        String min_PTO = PTO_min.getText().toString();
        String threshold_string = threshold.getText().toString();
        if (!"".equals(max_throttle)&&!"".equals(min_throttle)&&!"".equals(max_PTO)&&!"".equals(min_PTO)&&!"".equals(threshold_string)){
            maxthrt = Integer.parseInt(max_throttle);
            minthrt = Integer.parseInt(min_throttle);
            maxpto = Integer.parseInt(max_PTO);
            minpto = Integer.parseInt(min_PTO);
            thresh = Integer.parseInt(threshold_string);
        } else {
            Toast.makeText(AdvancedSettingsActivity.this,"Data fields have been left empty!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void set_text_value(){
        threshold.setText(Integer.toString(thresh));
        throttle_max.setText(Integer.toString(maxthrt));
        throttle_min.setText(Integer.toString(minthrt));
        PTO_max.setText(Integer.toString(maxpto));
        PTO_min.setText(Integer.toString(minpto));
    }
}