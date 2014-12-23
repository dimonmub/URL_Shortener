package ru.dimonmubmail.url_shortener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dmitriy Mubarakshin on 20.11.2014.
 */
public class MyObjectParser implements Parser<MyObject> {
    @Override
    public MyObject parse(String json) throws JSONException {
        MyObject myObject = new MyObject();
        JSONObject response = new JSONObject(json);
        myObject.setLongResult(response.getString("longUrl"));
        JSONObject analytics = response.getJSONObject("analytics");
        JSONObject allTime = analytics.getJSONObject("allTime");
        myObject.setShortURLClicks(allTime.getString("shortUrlClicks"));
        myObject.setLongURLClicks(allTime.getString("longUrlClicks"));

        JSONArray countries = allTime.getJSONArray("countries");
        for (int i = 0; i < countries.length(); i++) {
            JSONObject itemJson = countries.getJSONObject(i);
            myObject.setCountry(itemJson.getString("id"));
        }

        JSONArray browsers = allTime.getJSONArray("browsers");
        for (int i = 0; i < browsers.length(); i++) {
            JSONObject itemJson = browsers.getJSONObject(i);
            myObject.setBrowser(itemJson.getString("id"));
        }

        JSONArray platforms = allTime.getJSONArray("platforms");
        for (int i = 0; i < platforms.length(); i++) {
            JSONObject itemJson = platforms.getJSONObject(i);
            myObject.setPlatform(itemJson.getString("id"));
        }

        return myObject;
    }

}
