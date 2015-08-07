package uncap.ltfe.uncaptest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by LTFE on 24/07/15.
 */
public class CommunicationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }
    /**
     * IRemote definition is available here
     */
    private final ICommunication.Stub mBinder = new ICommunication.Stub() {

        @Override
        public int add(int a, int b) throws RemoteException {
            // TODO Auto-generated method stub
            return (a + b);
        }

        @Override
        public String showText(String text) throws RemoteException {
            // TODO Auto-generated method stub
            return text;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String showWidgetHTML() throws RemoteException {

            Log.d("AIDL", "showWidgetHTML");


            String customHtml = "<html>" +
            "<body>" +
            "<h2>UNCAP Driver Example</h2>" +
            "<h4>Choose unit and press SAVE:</h4>" +
            "<input onClick='setUnitRadioMgdl()' type='radio' name='unit' value='mgdl' checked>mg/dl<br>" +
            "<input onClick='setUnitRadioMmoll()' type='radio' name='unit' value='mmoll'>mmol/l<br><br>" +
            "<button onClick='setUnit()'>SAVE</button>" +
            "<script type='text/javascript'>" +
            "function setUnitRadioMgdl(){" +
            "WebViewActivityInterface.setUnitRadioMgdl();}" +
            "function setUnitRadioMmoll(){" +
            "WebViewActivityInterface.setUnitRadioMmoll();}" +
            "function setUnit(){" +
            "WebViewActivityInterface.setUnit();}" +
            "</script>" +
            "</body>" +
            "</html>";

            sendBroadcastMsgToMainActivity("showWidgetHTML");

            return customHtml;
        }

        @Override
        public void sendIntent(String parms) throws RemoteException {

            Log.d("AIDL", parms);

            sendBroadcastMsgToMainActivity(parms);

        }

    };

    void sendBroadcastMsgToMainActivity(String parms)
    {
        Intent i = new Intent("MSG_FROM_COM_SER_AIDL");
        i.putExtra("Msg",parms);

        sendBroadcast(i);
    }
}
