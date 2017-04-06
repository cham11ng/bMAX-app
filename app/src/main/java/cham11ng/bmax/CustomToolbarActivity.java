package cham11ng.bmax;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class CustomToolbarActivity extends AppCompatActivity {

    Toolbar bmaxToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toolbar);

        bmaxToolbar = (Toolbar) findViewById(R.id.bmaxToolbar);

        setSupportActionBar(bmaxToolbar);                   // custom toolbar
        ActionBar actionBar = getSupportActionBar();    //

        actionBar.setDisplayShowTitleEnabled(false);    // app name title is display is disabled
        actionBar.setHomeButtonEnabled(true);           // back button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
