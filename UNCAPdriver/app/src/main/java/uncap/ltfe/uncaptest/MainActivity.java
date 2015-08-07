package uncap.ltfe.uncaptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView myAwesomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAwesomeTextView = (TextView)findViewById(R.id.textViewDebug);

        //Register for getting messages from Communication Service
        registerReceiver(uiUpdated, new IntentFilter("MSG_FROM_COM_SER_AIDL"));
    }

    //Broadcast receiver
    private BroadcastReceiver uiUpdated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            myAwesomeTextView.setText("Received data from MAIN UNCAP APP: " + intent.getExtras().getString("Msg"));

        }
    };

    protected void onDestroy() {

        unregisterReceiver(uiUpdated);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
