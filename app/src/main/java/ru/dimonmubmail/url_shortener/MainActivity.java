package ru.dimonmubmail.url_shortener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnGetResult;
    EditText longURL;
    TextView result;
    ListView links;
    static String address="https://www.googleapis.com/urlshortener/v1/url";
    ArrayAdapterItem adapter;
    ArrayList<ObjectItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetResult = (Button) findViewById(R.id.getShortURL);
        longURL = (EditText) findViewById(R.id.longURL);
        result = (TextView) findViewById(R.id.result);
        links = (ListView) findViewById(R.id.links);

        data = new ArrayList<ObjectItem>();
        adapter = new ArrayAdapterItem(this, R.layout.item, data);
        links.setAdapter(adapter);

        View.OnClickListener oclBtnGetResult = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URLShort urlShort = new URLShort();
                urlShort.execute();
            }
        };

        btnGetResult.setOnClickListener(oclBtnGetResult);
    }

    private class URLShort extends AsyncTask<String, String, JSONObject> {
        String str_longURL, str_shortURL;
        protected void onPreExecute() {
            super.onPreExecute();
            str_longURL = longURL.getText().toString();
        }

        protected JSONObject doInBackground(String... args) {
            URLProcessing urlProcessing = new URLProcessing();
            JSONObject json = urlProcessing.getJSON(address, str_longURL);
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            try {
                if (json != null) {
                    str_shortURL = json.getString("id");
                    result.setText(str_shortURL);
                    data.add(new ObjectItem(str_shortURL));
                    adapter.notifyDataSetChanged();
                }
                else result.setText("Error");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
