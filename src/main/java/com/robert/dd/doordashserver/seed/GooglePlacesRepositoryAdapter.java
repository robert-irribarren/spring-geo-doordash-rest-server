package com.robert.dd.doordashserver.seed;

import com.google.maps.model.PlacesSearchResult;
import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.model.Merchant;
import com.robert.dd.doordashserver.model.MerchantCategory;
import com.robert.dd.doordashserver.model.MerchantProduct;
import com.robert.dd.doordashserver.repository.MerchantProductRepository;
import com.robert.dd.doordashserver.repository.MerchantRepository;
import com.robert.dd.doordashserver.utils.CommonMenuFactory;
import com.robert.dd.doordashserver.utils.MerchantType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores common merchants into doordash server
 */
@Service
public class GooglePlacesRepositoryAdapter {

    Logger logger = LoggerFactory.getLogger(GooglePlacesRepositoryAdapter.class);

    private GeometryFactory gf;
    private List<MerchantCategory> categoriess;

    @Autowired
    private CommonMenuFactory commonMenuFactory;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantProductRepository merchantProductRepository;
    //@Autowired
    //private MerchantCategoryRepository merchantCategoryRepository;

    public GooglePlacesRepositoryAdapter(){
        gf = new GeometryFactory();
        //categoriess
    }

    @PostConstruct
    public void init(){
        //categoriess =
    }



    private MerchantType resolveMerchantName(String merchantName){
        if (merchantName==null || merchantName.isEmpty())
            return MerchantType.NONE;
        switch(merchantName.toUpperCase()){
            case "CHIPOTLE": return MerchantType.CHIPOTLE;
            case "MCDONALDS": return MerchantType.MCDONALDS;
            case "PANDA EXPRESS": return MerchantType.PANDA_EXPRESS;
            case "WENDYS": return MerchantType.WENDYS;
            case "DENNYS": return MerchantType.DENNYS;
            case "TEXAS ROADHOUSE": return MerchantType.TEXAS_ROADHOUSE;
            case "OLIVE GARDEN": return MerchantType.OLIVE_GARDEN;
            case "CHILIS": return MerchantType.CHILIS;
            case "RED LOBSTER": return MerchantType.RED_LOBSTER;
            case "OUTBACK STEAKHOUSE": return MerchantType.OUTBACK_STEAKHOUSE;
            case "APPLEBEES": return MerchantType.APPLEBEES;
            case "BUFFALO WILD WINGS": return MerchantType.BUFFALO_WILD_WINGS;
            case "IHOP": return MerchantType.IHOP;
            default: return MerchantType.NONE;
        }
    }
    public void save(PlacesSearchResult result, String merchantName){

        MerchantType type = resolveMerchantName(merchantName);
        double lat = result.geometry.location.lat;
        double lng = result.geometry.location.lng;
        Merchant merchant = new Merchant();
        merchant.setName(result.name);
        merchant.setPlaceId(result.placeId);
        Address address = new Address();
        if (result.vicinity!=null || result.vicinity.contains(",")==false) {
            String[] addressTokens = result.vicinity.split(",");
            address.setAddress1(addressTokens[0]);
            address.setCity(addressTokens[1]);
            Point point = gf.createPoint(new Coordinate(lat,lng));
            address.setLocation(point);
            address.setCountry("US");
            merchant.setAddress(address);
        } else {
            logger.error("Google place: "+result.placeId + "did not have a proper vicinity, could not create address");
            return;
        }

        // check if restaurant exists already
        Merchant duplicateCheck = merchantRepository.findMerchantByNameAddress(merchant.getName(),merchant.getAddress().getAddress1());
        if ( duplicateCheck != null ){
            logger.info("Already saved this "+duplicateCheck.getName()+" at "+duplicateCheck.getAddress().getAddress1());
            return;
        }
        Merchant saved = merchantRepository.save(merchant);
        String merchId = saved.getId();

        logger.info("Saved a "+saved.getName() + "@"+saved.getAddress().getAddress1()+", "+saved.getAddress().getCity());


        List<MerchantProduct> products = new ArrayList<>();
        products.addAll(commonMenuFactory.getProductsFor(type));
        if (products!=null){
            for (MerchantProduct product : products){
                product.setMerchantId(merchId);
                //logger.info("Saving a product with merchantId" + merchId);
                merchantProductRepository.save(product);
                product.setId(null);
            }
        }
    }
}
