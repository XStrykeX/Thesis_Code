package dk.ccm_ee_dragosandreipetrut.thesisapplicationcorrected;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StandardSettingsActivity extends AppCompatActivity {

    Button advanced_settings, back2menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_settings);

        //Make screen on landscape only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        back2menu = findViewById(R.id.buttonBack2Main2);
        advanced_settings = findViewById(R.id.buttonAdvancedSettings2);

        back2menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2menu = new Intent(StandardSettingsActivity.this, MainActivity.class);
                startActivity(back2menu);
            }
        });

        advanced_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent advanced_settings = new Intent(StandardSettingsActivity.this, AdvancedSettingsActivity.class);
                startActivity(advanced_settings);
            }
        });
    }
}