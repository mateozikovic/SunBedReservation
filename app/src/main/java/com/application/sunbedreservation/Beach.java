package com.application.sunbedreservation;

/**
 * Beach object for firebase
 */

public class Beach {
    public String title, subTitle, picture, location, freeSunbeds;

    public Beach(){

    }

    public Beach(String title, String subTitle, String picture, String location, String freeSunbeds){
        this.title = title;
        this.subTitle = subTitle;
        this.picture = picture;
        this.location = location;
        this.freeSunbeds = freeSunbeds;
    }
}
