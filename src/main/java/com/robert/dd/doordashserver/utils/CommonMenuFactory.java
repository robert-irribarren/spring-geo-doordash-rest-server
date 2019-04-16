package com.robert.dd.doordashserver.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.robert.dd.doordashserver.model.MerchantProduct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Loads the common menus for populating
 * our application's restaurants with lazy loading
 */
@Service
public class CommonMenuFactory {

    private Gson gson;
    private Map<String,List<MerchantProduct>> data;
    public CommonMenuFactory(){
        gson = new Gson();
        data = new HashMap<>();
    }

    public List<MerchantProduct> getProductsFor(MerchantType type){
        switch (type){
            case CHIPOTLE: return getMenu("common_menus/CHIPOTLE.json");
            case MCDONALDS: return getMenu("common_menus/MCDONALDS.json");
            case PANDA_EXPRESS: return getMenu("common_menus/PANDA_EXPRESS.json");
            case WENDYS: return getMenu("common_menus/WENDYS.json");
            case DENNYS: return getMenu("common_menus/DENNYS.json");
            default: return null;
        }
    }

    private List<MerchantProduct> getMenu(String menuFile){
        if (data.containsKey(menuFile))
            return data.get(menuFile);

        try {
            Type myDataType = new TypeToken<List<MerchantProduct>>(){}.getType();

            Resource resource = new ClassPathResource(menuFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);
            List<MerchantProduct> myData = gson.fromJson(br, myDataType);
            data.put(menuFile,myData);
            return myData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
