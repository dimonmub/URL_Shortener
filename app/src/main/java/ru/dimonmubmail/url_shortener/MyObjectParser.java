package ru.dimonmubmail.url_shortener;

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
        myObject.setKind(response.getString("kind"));
        myObject.setId(response.getString("id"));
        myObject.setLongUrl(response.getString("longUrl"));
        myObject.setStatus(response.getString("status"));
        return myObject;
    }

}
