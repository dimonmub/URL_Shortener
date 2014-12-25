package ru.dimonmubmail.url_shortener;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dmitriy Mubarakshin on 25.12.2014.
 */
@DatabaseTable(tableName="links")
public class Link implements Serializable {

    public static final String LINK_ID = "link_id";
    public static final String LINK_TEXT = "link_text";

    @DatabaseField(columnName = LINK_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = LINK_TEXT)
    private String text;

    public Link() {

    }
    public Link(String text) {
        this.text = text;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }
}
