package lanet.bhavin.rxjavasample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import lanet.bhavin.rxjavasample.models.User;

public class UserDetailActivity extends AppCompatActivity {

    TextView tvUserDisplayName;
    TextView tvUserLocation;
    private static final String TAG = "UserDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        tvUserDisplayName = (TextView) findViewById(R.id.tvUserDisplayName);
        tvUserLocation = (TextView) findViewById(R.id.tvUserLocation);
        User user = getIntent().getParcelableExtra("User");
        Log.d(TAG, "onCreate: :"+user.toString());
        tvUserDisplayName.setText(user.getDisplay_name());
        tvUserLocation.setText(user.getLocation());
    }

}
