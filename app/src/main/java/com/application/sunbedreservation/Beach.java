package com.application.sunbedreservation;

/**
 * Beach object for firebase
 */

public class Beach {
    public String title, subTitle, locationLat, locationLng, freeSunbeds;

    public Beach(){

    }

    public Beach(String title, String subTitle, String locationLat, String locationLng, String freeSunbeds){
        this.title = title;
        this.subTitle = subTitle;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.freeSunbeds = freeSunbeds;
    }
}
