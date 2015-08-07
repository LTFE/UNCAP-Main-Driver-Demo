package uncap.ltfe.uncaptest;

import android.app.Activity;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by LTFE on 24/07/15.
 */
public class WebViewActivity extends Activity {

    private WebView webView;
    Context context;

    protected ICommunication mServiceCon;
    ServiceConnection mServiceConnectionCon;

    String unitHtmlGlucose;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        context = this;

        unitHtmlGlucose = "mg/dl";

        //Web page
        showWebPage();

    }

    protected void onDestroy() {

        super.onDestroy();
    };


    void showWebPage(){

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(), "WebViewActivityInterface");
        //webView.loadUrl("http://www.google.com");

        String customHtmlAidl = ((GlobalData) this.getApplication()).getHtmlVariable();
        webView.loadData(customHtmlAidl, "text/html", "UTF-8");


    }

    // JAVA SCRIPT INTERFACE
    public class WebAppInterface {

        // Show a toast from the web page
        @JavascriptInterface
        public void setUnitRadioMmoll() {
            //Toast.makeText(context, "UNCAP Interface Called", Toast.LENGTH_LONG).show();
            unitHtmlGlucose = "mmol/l";
        }

        @JavascriptInterface
        public void setUnitRadioMgdl() {
            //Toast.makeText(context, "UNCAP Interface Called", Toast.LENGTH_LONG).show();
            unitHtmlGlucose = "mg/dl";
        }

        @JavascriptInterface
        public void setUnit() {
            Toast.makeText(context, "UNCAP unit set to " + unitHtmlGlucose + " and send to driver.", Toast.LENGTH_LONG).show();

            saveParmsForIntent(unitHtmlGlucose);
            finish();
        }

    }

    void saveParmsForIntent(String parmsSend)
    {
        ((GlobalData) this.getApplication()).setIntentItemVariable(parmsSend);
    }

}