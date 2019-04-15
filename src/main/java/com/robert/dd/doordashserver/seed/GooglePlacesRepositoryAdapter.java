package com.robert.dd.doordashserver.seed;

import com.google.maps.model.PlacesSearchResult;
import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.model.Merchant;
import com.robert.dd.doordashserver.model.MerchantCategory;
import com.robert.dd.doordashserver.model.MerchantProduct;
import com.robert.dd.doordashserver.repository.MerchantProductRepository;
import com.robert.dd.doordashserver.repository.MerchantRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    enum MerchantType {
        CHIPOTLE,
        MCDONALDS,
        PANDA_EXPRESS,
        WENDYS,
        DENNYS,
        TEXAS_ROADHOUSE,
        OLIVE_GARDEN,
        CHILIS,
        RED_LOBSTER,
        OUTBACK_STEAKHOUSE,
        APPLEBEES,
        BUFFALO_WILD_WINGS,
        IHOP
    }

    public void save(PlacesSearchResult result, MerchantType type){

        double lat = result.geometry.location.lat;
        double lng = result.geometry.location.lng;
        Merchant merchant = new Merchant();
        merchant.setName(result.name);
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


        if (type==MerchantType.CHIPOTLE){
            MerchantProduct beanBurrito = new MerchantProduct();
            beanBurrito.setName("Bean Burrito");
            beanBurrito.setPrice(9.10);
            beanBurrito.setGroupName("burrito");
            beanBurrito.setDescription("A bean burrito");
            beanBurrito.setMerchantId(merchId);
            merchantProductRepository.save(beanBurrito);

            MerchantProduct chickenBurrito = new MerchantProduct();
            chickenBurrito.setName("Chicken Burrito");
            chickenBurrito.setPrice(12.10);
            chickenBurrito.setGroupName("burrito");
            chickenBurrito.setDescription("A chicken burrito");
            chickenBurrito.setMerchantId(merchId);
            merchantProductRepository.save(chickenBurrito);

            MerchantProduct steakBurrito = new MerchantProduct();
            steakBurrito.setName("Steak Burrito");
            steakBurrito.setPrice(13.10);
            steakBurrito.setGroupName("burrito");
            steakBurrito.setDescription("A steak burrito");
            steakBurrito.setMerchantId(merchId);
            merchantProductRepository.save(steakBurrito);

            MerchantProduct chips = new MerchantProduct();
            chips.setName("Chips");
            chips.setPrice(2.40);
            chips.setGroupName("small orders");
            chips.setDescription("Salted chips, chipotle style");
            chips.setMerchantId(merchId);
            merchantProductRepository.save(chips);

            logger.info("Added a chipotle: "+merchId);
        } else if (type==MerchantType.MCDONALDS) {
            MerchantProduct fries = new MerchantProduct();
            fries.setName("Fries");
            fries.setPrice(3.10);
            fries.setGroupName("sides");
            fries.setDescription("Fried potatoes with salt and ketchup");
            fries.setMerchantId(merchId);
            merchantProductRepository.save(fries);

            MerchantProduct bigMac = new MerchantProduct();
            bigMac.setName("Big Mac");
            bigMac.setPrice(6.10);
            bigMac.setGroupName("burgers");
            bigMac.setDescription("Our world famous burger");
            bigMac.setMerchantId(merchId);
            merchantProductRepository.save(bigMac);

            MerchantProduct chickenNuggets = new MerchantProduct();
            chickenNuggets.setName("Chicken McNuggets");
            chickenNuggets.setPrice(5.10);
            chickenNuggets.setGroupName("sides");
            chickenNuggets.setDescription("Chicken Nuggets with great flavor");
            chickenNuggets.setMerchantId(merchId);
            merchantProductRepository.save(chickenNuggets);
        } else if (type == MerchantType.PANDA_EXPRESS){

            MerchantProduct orangeChicken = new MerchantProduct();
            orangeChicken.setName("Orange Chicken");
            orangeChicken.setPrice(3.10);
            orangeChicken.setGroupName("main course");
            orangeChicken.setDescription("Authentic orange chicken");
            orangeChicken.setMerchantId(merchId);
            merchantProductRepository.save(orangeChicken);

            MerchantProduct tsaoChicken = new MerchantProduct();
            tsaoChicken.setName("General Tsao Chicken");
            tsaoChicken.setPrice(5.10);
            tsaoChicken.setGroupName("main course");
            tsaoChicken.setDescription("Savory chicken");
            tsaoChicken.setMerchantId(merchId);
            merchantProductRepository.save(tsaoChicken);

            MerchantProduct friedRice = new MerchantProduct();
            friedRice.setName("Fried Rice");
            friedRice.setPrice(3.10);
            friedRice.setGroupName("rice plates");
            friedRice.setDescription("Rice with vegetables");
            friedRice.setMerchantId(merchId);
            merchantProductRepository.save(friedRice);
        }
    }
}
