package ru.dimonmubmail.url_shortener;

import java.io.Serializable;

/**
 * Created by Dmitriy Mubarakshin on 20.11.2014.
 */
public class MyObject implements Serializable{
    private String kind;
    private String id;
    private String longUrl;
    private String status;

    public void setKind (String kind) {
        this.kind = kind;
    }

    public void setId (String id) {
        this.id = id;
    }

    public void setLongUrl (String longUrl) {
        this.longUrl = longUrl;
    }

    public void setStatus (String status) {
        this.status = status;
    }
}
