package ru.dimonmubmail.url_shortener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;


public class ParametersActivity extends Activity {

    public static final String LINK = "extra_link";
    TextView shortResult;
    TextView longResult;
    TextView shortURLClicks;
    TextView longURLClicks;
    TextView country;
    TextView browser;
    TextView platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        shortResult = (TextView) findViewById(R.id.shortResult);
        longResult = (TextView) findViewById(R.id.longResult);
        shortURLClicks = (TextView) findViewById(R.id.shortURLClicks);
        longURLClicks = (TextView) findViewById(R.id.longURLClicks);
        country = (TextView) findViewById(R.id.pCountry);
        browser = (TextView) findViewById(R.id.pBrowser);
        platform = (TextView) findViewById(R.id.pPlatform);
        String extra = getIntent().getStringExtra(LINK);
        shortResult.setText(extra);
        getParameters gp = new getParameters();
        gp.execute();
    }

    private class getParameters extends AsyncTask<String,String,MyObject> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MyObject doInBackground(String... args) {
            URLProcessing urlProcessing = new URLProcessing((String) shortResult.getText());
            MyObject myObject = new MyObject();
            try {
                myObject = urlProcessing.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return myObject;
        }

        protected void onPostExecute(MyObject myObject) {
            longResult.setText(myObject.getLongResult());
            shortURLClicks.setText(myObject.getShortURLClicks());
            longURLClicks.setText(myObject.getLongURLClicks());
            country.setText(myObject.getCountry());
            browser.setText(myObject.getBrowser());
            platform.setText(myObject.getPlatform());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parameters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
