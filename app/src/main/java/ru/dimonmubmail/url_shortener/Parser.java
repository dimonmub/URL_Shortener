package ru.dimonmubmail.url_shortener;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by Dmirtiy Mubarakshin on 20.11.2014.
 */
public interface Parser<T extends Serializable> {

    public T parse(String json) throws JSONException;

}
