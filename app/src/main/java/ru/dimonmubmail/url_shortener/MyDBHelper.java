package ru.dimonmubmail.url_shortener;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Dmitriy Mubarakshin on 24.12.2014.
 */
public class MyDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "links.db";
    private static final int DATABASE_VERSION = 1;

    private LinkDAO linkDAO;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Link.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Link.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkDAO getLinkDAO() throws SQLException {
        if (linkDAO == null)
            linkDAO = new LinkDAO(getConnectionSource());
        return linkDAO;
    }

    public static MyDBHelper getInstance(Context context) {
        return OpenHelperManager.getHelper(context, MyDBHelper.class);
    }
}
