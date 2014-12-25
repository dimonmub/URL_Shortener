package ru.dimonmubmail.url_shortener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnGetResult;
    EditText longURL;
    TextView result;
    ListView links;
    static String address="https://www.googleapis.com/urlshortener/v1/url";
    ArrayAdapterItem adapter;
    ArrayList<Link> data;
    Button btnAddToDB;
    Link link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetResult = (Button) findViewById(R.id.getShortURL);
        longURL = (EditText) findViewById(R.id.longURL);
        result = (TextView) findViewById(R.id.result);
        links = (ListView) findViewById(R.id.links);
        btnAddToDB = (Button) findViewById(R.id.addToDB);

        data = new ArrayList<Link>();
        try {
            data = new ArrayList<Link>(MyDBHelper.getInstance(MainActivity.this).getLinkDAO().getAllLinks());
            Toast.makeText(MainActivity.this, "Links loaded",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapterItem(this, R.layout.item, data);
        links.setAdapter(adapter);
        links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ParametersActivity.class);
                intent.putExtra(ParametersActivity.LINK, data.get(i).getText());
                startActivity(intent);
            }
        });

        View.OnClickListener oclBtnGetResult = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URLShort urlShort = new URLShort();
                urlShort.execute();
            }
        };

        View.OnClickListener oclBtnAddToDB = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    link = new Link((String)result.getText());
                    MyDBHelper dbHelper = MyDBHelper.getInstance(MainActivity.this);
                    dbHelper.getLinkDAO().create(link);
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        btnGetResult.setOnClickListener(oclBtnGetResult);
        btnAddToDB.setOnClickListener(oclBtnAddToDB);
    }

    private class URLShort extends AsyncTask<String, String, JSONObject> {
        String str_longURL, str_shortURL;
        protected void onPreExecute() {
            super.onPreExecute();
            str_longURL = longURL.getText().toString();
        }

        protected JSONObject doInBackground(String... args) {
            URLProcessing urlProcessing = new URLProcessing(new String());
            JSONObject json = urlProcessing.getJSON(address, str_longURL);
            try {
                str_shortURL = json.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                result.setText(str_shortURL);
                data.add(new Link(str_shortURL));
                adapter.notifyDataSetChanged();
            }
                else result.setText("Error");
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
