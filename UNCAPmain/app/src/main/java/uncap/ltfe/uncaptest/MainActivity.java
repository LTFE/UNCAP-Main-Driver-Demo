package uncap.ltfe.uncaptest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button buttonGetWidget;
    TextView textViewDebug;
    protected ICommunication mService;
    ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGetWidget = (Button) findViewById(R.id.buttonGetWidget);
        buttonGetWidget.setOnClickListener((android.view.View.OnClickListener) this);
        buttonGetWidget.setBackgroundColor(Color.RED);

        textViewDebug = (TextView) findViewById(R.id.textViewDebug);

        ((GlobalData) this.getApplication()).setIntentItemVariable("");

        initConnection();
    }

    void initConnection(){
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                mService = null;
                Toast.makeText(getApplicationContext(), "UNCAP main<->driver disconnected", Toast.LENGTH_SHORT).show();
                Log.d("ICommunication", "Binding - Service disconnected");
                buttonGetWidget.setBackgroundColor(Color.RED);
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                // TODO Auto-generated method stub
                mService = ICommunication.Stub.asInterface((IBinder) service);
                Toast.makeText(getApplicationContext(), "UNCAP main<->driver connected", Toast.LENGTH_SHORT).show();
                Log.d("ICommunication", "Binding is done - Service connected");
                buttonGetWidget.setBackgroundColor(Color.GREEN);
            }
        };

        if(mService == null)
        {

            Intent intent = new Intent();
            intent.setAction("uncap.ltfe.uncapdriver.COM");
            intent.setPackage("uncap.ltfe.uncapdriver");
            //binding to remote service
            bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);

        }
    }

    @Override
    public void onResume(){
        super.onResume();

        String variable = ((GlobalData) this.getApplication()).getIntentItemVariable();

        Log.d("ICommunication", "onResume "+ variable);

        if (variable.length() > 0)
        {
            sendCustomIntent(variable);
            ((GlobalData) this.getApplication()).setIntentItemVariable("");
        }

    }

    protected void onDestroy() {

        Log.d("ICommunication", "onDestroy");

        super.onDestroy();
        unbindService(mServiceConnection);
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


    @Override
    public void onClick(View v) {
        Log.d("buttonGetWidget", "buttonGetWidget");


        try{
            ((GlobalData) this.getApplication()).setHtmlVariable(mService.showWidgetHTML().toString());
            //Log.d("buttonGetWidget", mService.showWidgetHTML().toString());

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        startActivity(intent);
    }

    public void sendCustomIntent(String intentPayload){

        if(mService != null)
        {
            Log.d("ICommunication", "sendCustomIntent YES " + intentPayload);

            try {
                mService.sendIntent(intentPayload);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.d("ICommunication", "sendCustomIntent NOT");
        }
    }



}
