package com.robert.dd.doordashserver.utils;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;

import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.geometry.primitive.Point;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;

import org.geotools.data.DataUtilities;
import org.geotools.feature.NameImpl;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.feature.type.GeometryDescriptorImpl;
import org.geotools.feature.type.GeometryTypeImpl;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;


import com.vividsolutions.jts.geom.Geometry;

import com.vividsolutions.jts.geom.Polygon;

import java.util.List;


public class GeoUtils {

    /**
     * Returns the distance between two coordinates given as longitude and
     * latitude
     */
    public static Long getDistance(final Double longitudeA, final Double latitudeA, final Double longitudeB, final Double latitudeB) {
        Double distance = 0.0;
        final GeodeticCalculator cal = new GeodeticCalculator();
        cal.setStartingGeographicPoint(longitudeA, latitudeA);
        cal.setDestinationGeographicPoint(longitudeB, latitudeB);
        distance = cal.getOrthodromicDistance();

        final int totalmeters = distance.intValue();
        final int km = totalmeters / 1000;
        float remaining_cm = (float) (distance - totalmeters) * 10000;
        remaining_cm = Math.round(remaining_cm);

        System.out.println(latitudeA + "," + longitudeA + " vs " + latitudeB + "," + longitudeB);
        System.out.println("Distance = " + totalmeters + "m");

        return distance.longValue();
    }


    public static Polygon createRadius(final Double latitude, final Double longitude, final Double radius) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(256); // adjustable
        shapeFactory.setCentre(new Coordinate(latitude, longitude));

        // Length in meters of 1° of latitude = always 111.13 km
        shapeFactory.setHeight(radius / 111130d);

        // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
        shapeFactory.setWidth(radius / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));

        Polygon circle = shapeFactory.createEllipse();
        return circle;
    }

    private static final String EPSG4326 = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]]";
    private static final String EPSG32630 = "PROJCS[\"WGS 84 / UTM zone 30N\",GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Transverse_Mercator\"],PARAMETER[\"latitude_of_origin\",0],PARAMETER[\"central_meridian\",-3],PARAMETER[\"scale_factor\",0.9996],PARAMETER[\"false_easting\",500000],PARAMETER[\"false_northing\",0],AUTHORITY[\"EPSG\",\"32630\"],AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH]]";

    public static Geometry createRadiusPoly(double latitude, double longitude) {


        CoordinateReferenceSystem geoCRS = null;
        CoordinateReferenceSystem utmCRS = null;
        MathTransform transformToUtm = null;
        try {
            geoCRS = CRS.parseWKT(EPSG4326); // CRS.decode("EPSG:4326");
            utmCRS = CRS.parseWKT(EPSG32630); //CRS.decode("EPSG:32630");
            transformToUtm = CRS.findMathTransform(geoCRS, utmCRS);
        } catch (FactoryException e) {
            e.printStackTrace();
        }

        GeometryFactory geometryFactory = new GeometryFactory();
        Geometry pointSource = geometryFactory.createPoint(new Coordinate(latitude, longitude)); // coordinateSource is lat=41.942667 lon=12.462218

        Geometry targetGeometry = null;
        try {
            targetGeometry = JTS.transform(pointSource, transformToUtm);
        } catch (TransformException e) {
            e.printStackTrace();
        }

        Geometry buffer = targetGeometry.buffer(80000);
        buffer.setSRID(32630);
        try {
            MathTransform transformToGeo = CRS.findMathTransform(utmCRS, geoCRS);

            Geometry bufferGeo = JTS.transform(buffer, transformToGeo);
            bufferGeo.setSRID(0);
            Geometry pointSource2 = geometryFactory.createPoint(new Coordinate(latitude, longitude)); // coordinateSource is lat=41.942667 lon=12.462218

            boolean worked = bufferGeo.contains(pointSource2);
            System.out.println("Does it contain? : " + worked);
            return bufferGeo;
        } catch (FactoryException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Geometry bufferPoint(double latitude, double longitude, double radius){
        SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

        //set the name
        b.setName( "MyFeatureType" );

        //add a geometry property
        b.setCRS( DefaultGeographicCRS.WGS84 ); // set crs first
        b.add( "location", com.vividsolutions.jts.geom.Point.class ); // then add geometry

        //build the type
        final SimpleFeatureType TYPE = b.buildFeatureType();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        com.vividsolutions.jts.geom.Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        featureBuilder.add(point);
        SimpleFeature feature = featureBuilder.buildFeature( "fid.1" ); // build the 1st feature
        Geometry buffedPoint = (Geometry)bufferFeature(feature,  Measure.valueOf(10.0, SI.KILOMETER)).getDefaultGeometry();
        boolean original = buffedPoint.contains(geometryFactory.createPoint(new Coordinate(longitude, latitude)));
        System.out.println("REALLY:" +original);
        return buffedPoint;
    }

    public static SimpleFeature bufferFeature(SimpleFeature feature, Measure<Double, Length> distance) {
        // extract the geometry
        GeometryAttribute gProp = feature.getDefaultGeometryProperty();
        CoordinateReferenceSystem origCRS = gProp.getDescriptor().getCoordinateReferenceSystem();

        Geometry geom = (Geometry) feature.getDefaultGeometry();
        Geometry pGeom = geom;
        MathTransform toTransform, fromTransform = null;
        // reproject the geometry to a local projection
        if (!(origCRS instanceof ProjectedCRS)) {

            double x = geom.getCoordinate().x;
            double y = geom.getCoordinate().y;

            String code = "AUTO:42001," + x + "," + y;
            // System.out.println(code);
            CoordinateReferenceSystem auto;
            try {
                auto = CRS.decode(code);
                toTransform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, auto);
                fromTransform = CRS.findMathTransform(auto, DefaultGeographicCRS.WGS84);
                pGeom = JTS.transform(geom, toTransform);

            } catch (MismatchedDimensionException | TransformException | FactoryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // buffer
        Geometry out = buffer(pGeom, distance.doubleValue(SI.METER));
        Geometry retGeom = out;
        // reproject the geometry to the original projection
        if (!(origCRS instanceof ProjectedCRS)) {
            try {
                retGeom = JTS.transform(out, fromTransform);

            } catch (MismatchedDimensionException | TransformException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // return a new feature containing the geom
        SimpleFeatureType schema = feature.getFeatureType();
        SimpleFeatureTypeBuilder ftBuilder = new SimpleFeatureTypeBuilder();
        ftBuilder.setCRS(origCRS);

        for (AttributeDescriptor attrib : schema.getAttributeDescriptors()) {
            AttributeType type = attrib.getType();

            if (type instanceof GeometryType) {
                String oldGeomAttrib = attrib.getLocalName();
                ftBuilder.add(oldGeomAttrib, Polygon.class);
            } else {
                ftBuilder.add(attrib);
            }
        }
        ftBuilder.setName(schema.getName());

        SimpleFeatureType nSchema = ftBuilder.buildFeatureType();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(nSchema);
        List<Object> atts = feature.getAttributes();
        for (int i = 0; i < atts.size(); i++) {
            if (atts.get(i) instanceof Geometry) {
                atts.set(i, retGeom);
            }
        }
        SimpleFeature nFeature = builder.buildFeature(null, atts.toArray());
        return nFeature;
    }

    /**
     * create a buffer around the geometry, assumes the geometry is in the same
     * units as the distance variable.
     *
     * @param geom
     *          a projected geometry.
     * @param dist
     *          a distance for the buffer in the same units as the projection.
     * @return
     */
    private static Geometry buffer(Geometry geom, double dist) {

        Geometry buffer = geom.buffer(dist);

        return buffer;

    }

}
