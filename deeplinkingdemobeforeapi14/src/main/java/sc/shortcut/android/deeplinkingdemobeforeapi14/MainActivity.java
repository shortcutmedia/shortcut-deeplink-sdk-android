package sc.shortcut.android.deeplinkingdemobeforeapi14;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import sc.shortcut.deeplinkingdemo.common.MainFragment;
import sc.shortcut.deeplinkingsdk.SCDeepLinking;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCDeepLinking deepLinking = SCDeepLinking.getInstance(this);
        deepLinking.start(getIntent());

        Uri deepLink = getIntent().getData();

        if (deepLink != null) {
            Log.d(TAG, "opened with deep link: " + deepLink);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new MainFragment())
                .commit();
    }

    @Override
    public void onRequestStartWithDeepLinking() {
        Intent viewIntent = new Intent();
        viewIntent.setAction(Intent.ACTION_VIEW);
        viewIntent.setData(Uri.parse("scdemo://shortcut.sc/demoapi14?link_id=384729"));
        startActivity(viewIntent);
    }

    @Override
    public void onRequestAppRestart() {
        Log.d(TAG, "onRequestAppRestart ");
        startActivity(new Intent(this, this.getClass()));
    }
}