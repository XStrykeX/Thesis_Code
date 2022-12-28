package dk.ccm_ee_dragosandreipetrut.thesisapplicationcorrected;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //Declare variables for GUI elements
    ProgressBar throttle_set_point, PTO_highlight;
    Button throttle_increase, throttle_decrease, PTO, settings, system_status;
    TextView throttle_level, PTO_level;
    VideoView video_feed;

    //Declare variables used for functionality
    private int throttle = 0;
    private boolean pto = true;
    private boolean connected = false;
    private int threshold;

    SharedPreferences technical_data;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make screen on landscape only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Associate GUI variables with xml elements from layout
        PTO = findViewById(R.id.buttonPTO);
        PTO_level = findViewById(R.id.textPTO);
        video_feed = findViewById(R.id.videoView);
        settings = findViewById(R.id.buttonSettings);
        PTO_highlight = findViewById(R.id.statusPTO);
        system_status = findViewById(R.id.buttonSystemStatus);
        throttle_level = findViewById(R.id.throttleSetpoint);
        throttle_increase = findViewById(R.id.increaseThrottle);
        throttle_decrease = findViewById(R.id.decreaseThrottle);
        throttle_set_point = findViewById(R.id.throttleSetpointBar);

        technical_data = getApplicationContext().getSharedPreferences("TechnicalData", MODE_PRIVATE);

        threshold = technical_data.getInt("Threshold", 50);

        throttle_set_point.setProgress(0);
        throttle_level.setText("0%");
        PTO_level.setText("OFF");

        if (connected){
            system_status.setText("System ONLINE");
            system_status.setTextColor(Color.parseColor("#FFFFFF"));
            system_status.setBackground(getDrawable(R.drawable.ccm_square_button));
        } else{
            system_status.setText("System OFFLINE");
            system_status.setTextColor(Color.parseColor("#275587"));
            system_status.setBackground(getDrawable(R.drawable.ccm_off_square_button));
        }

        throttle_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (throttle <= 75)
                {
                    throttle += 25;
                    updateProgressBar();
                }
            }
        });

        throttle_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (throttle >= 25)
                {
                    throttle -= 25;
                    updateProgressBar();
                }
            }
        });

        PTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (throttle > threshold){
                    PTO.setBackground(getDrawable(R.drawable.ccm_off_square_button));
                    Toast.makeText(MainActivity.this,"Throttle level too high!", Toast.LENGTH_SHORT).show();
                }else{
                    PTO.setBackground(getDrawable(R.drawable.ccm_square_button));
                if (pto){
                    PTO_highlight.setProgress(100);
                    PTO_level.setText("ON");
                    pto = false;
                }else {
                    PTO_highlight.setProgress(0);
                    PTO_level.setText("OFF");
                    pto = true;
                }}
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean thrt;
                if (throttle > 0){
                    thrt = true;
                }else {
                    thrt = false;
                }

                if (!pto||thrt){
                    settings.setBackground(getDrawable(R.drawable.ccm_off_square_button));
                    Toast.makeText(MainActivity.this,"System must be completely shut off before enabling settings!", Toast.LENGTH_SHORT).show();
                }else {
                    settings.setBackground(getDrawable(R.drawable.ccm_square_button));
                    Intent go_to_settings = new Intent(MainActivity.this, StandardSettingsActivity.class);
                    startActivity(go_to_settings);
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void updateProgressBar()
    {
        throttle_set_point.setProgress(throttle);
        throttle_level.setText(throttle + "%");
    }
}