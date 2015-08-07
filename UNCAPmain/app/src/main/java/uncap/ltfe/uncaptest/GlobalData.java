package uncap.ltfe.uncaptest;

import android.app.Application;

/**
 * Created by LTFE on 24/07/15.
 */
public class GlobalData extends Application {

    // HTML variable
    private String htmlVariable;
    public String getHtmlVariable() {
        return htmlVariable;
    }
    public void setHtmlVariable(String htmlVariable) {
        this.htmlVariable = htmlVariable;
    }

    // Intent item variable
    private String intentItemVariable;
    public String getIntentItemVariable() {
        return intentItemVariable;
    }
    public void setIntentItemVariable(String intentItemVariable) {
        this.intentItemVariable = intentItemVariable;
    }
}
