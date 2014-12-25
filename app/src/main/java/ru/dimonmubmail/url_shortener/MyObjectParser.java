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

        if (allTime.optJSONArray("countries") != null)
        {
            JSONArray countries = allTime.getJSONArray("countries");
            JSONObject itemJson = countries.getJSONObject(0);
            myObject.setCountry(itemJson.getString("id"));
        }
        else
            myObject.setCountry("error");

        if (allTime.optJSONArray("browsers") != null)
        {
            JSONArray browsers = allTime.getJSONArray("browsers");
            JSONObject itemJson = browsers.getJSONObject(0);
            myObject.setBrowser(itemJson.getString("id"));
        }
        else
            myObject.setBrowser("error");

        if (allTime.optJSONArray("platforms") != null)
        {
            JSONArray platforms = allTime.getJSONArray("platforms");
            JSONObject itemJson = platforms.getJSONObject(0);
            myObject.setPlatform(itemJson.getString("id"));
        }
        else
            myObject.setPlatform("error");

        return myObject;
    }

}
