package com.robert.dd.doordashserver.seed;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * This will generate a doordash environment given a latitude longitude
 */
@Component
public class GooglePlacesSeed {

    @Autowired
    private GooglePlacesRepositoryAdapter googlePlacesRepositoryAdapter;
    @Value("#{'${seed_stores}'.split(',')}")
    private List<String> stores;

    // this is from your environment variables
    @Value("${DD_GOOGLE_PLACES_API_KEY}")
    private String API_KEY;

    public void seed(double[][] points){
        for (double[] p : points){
            seed(p);
        }
    }
    public void seed(double[] point){
        for (String store : stores) {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build();
            LatLng latLng = new LatLng(point[0], point[1]);
            NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(context, latLng);
            nearbySearchRequest.keyword(store);
            nearbySearchRequest.radius(30000); //max is 50000
            PlacesSearchResponse newPlacesSearchResponse = null;
            try {
                newPlacesSearchResponse = nearbySearchRequest.await();
                for (PlacesSearchResult response : newPlacesSearchResponse.results) {
                    googlePlacesRepositoryAdapter.save(response, store);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
