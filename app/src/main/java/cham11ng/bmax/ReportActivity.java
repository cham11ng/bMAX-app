package cham11ng.bmax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {
    public static final String TAG = "ReportActivity";

    TextView datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the Title bar of this activity screen
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_report);

        datas = (TextView) findViewById(R.id.datas);

        String name = getIntent().getStringExtra("name");

        String gender = getIntent().getStringExtra("gender");
        String agroup = getIntent().getStringExtra("agroup");
        String symptoms = getIntent().getStringExtra("symptoms");
        String response = getIntent().getStringExtra("response");

        Log.d(TAG, name+"\n"+gender+"\n"+gender+"\n"+agroup+"\n"+symptoms+"\n"+response);
        datas.setText(name+"\n"+gender+"\n"+gender+"\n"+agroup+"\n"+symptoms+"\n"+response);
    }
}
