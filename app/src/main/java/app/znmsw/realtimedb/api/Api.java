package app.znmsw.realtimedb.api;

import java.util.List;

import app.znmsw.realtimedb.model.Hero;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by WT on 10/11/2017.
 */

public interface Api {
    //String BASE_URL = "https://simplifiedcoding.net/demos/";
     String BASE_URL = "https://realtimedb-67378.firebaseio.com/";
    //String BASE_URL = " http://www.falconbreeze.com/mobiletest/";

    // @GET("marvel")
    @GET("book.json")
    Call<List<Hero>> getHeroes();
}
