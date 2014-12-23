package ru.dimonmubmail.url_shortener;

import java.io.Serializable;

/**
 * Created by Dmitriy Mubarakshin on 20.11.2014.
 */
public class MyObject implements Serializable{
    private String longResult;
    private String shortURLClicks;
    private String longURLClicks;
    private String country;
    private String browser;
    private String platform;

    public String getLongResult () { return longResult; }

    public String getShortURLClicks () { return shortURLClicks; }

    public String getLongURLClicks () { return longURLClicks; }

    public String getCountry () { return country; }

    public String getBrowser () { return browser; }

    public String getPlatform () { return platform; }

    public void setLongResult (String longResult) { this.longResult = longResult; }

    public void setShortURLClicks (String shortURLClicks) {
        this.shortURLClicks = shortURLClicks;
    }

    public void setLongURLClicks (String longURLClicks) {
        this.longURLClicks = longURLClicks;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public void setBrowser (String browser) {
        this.browser = browser;
    }

    public void setPlatform (String platform) { this.platform = platform; }
}
