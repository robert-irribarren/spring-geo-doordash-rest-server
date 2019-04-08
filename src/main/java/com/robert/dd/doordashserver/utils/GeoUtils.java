package com.robert.dd.doordashserver.utils;
import com.vividsolutions.jts.geom.*;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.datum.DefaultEllipsoid;

import java.awt.geom.Point2D;


public class GeoUtils {

    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final GeodeticCalculator calc = new GeodeticCalculator(DefaultEllipsoid.WGS84);


    /**
     * Returns the distance between two coordinates given as longitude and
     * latitude
     * @param longitudeA Degrees of longitude
     * @param latitudeA Degrees of latitude
     * @param longitudeB Degrees of longitude
     * @param latitudeB Degrees of latitude
     * @return long The distance between two SRID 4326 points
     */
    public static synchronized Long getDistance(final Double longitudeA, final Double latitudeA, final Double longitudeB, final Double latitudeB) {
        final GeodeticCalculator cal = new GeodeticCalculator();
        cal.setStartingGeographicPoint(longitudeA, latitudeA);
        cal.setDestinationGeographicPoint(longitudeB, latitudeB);
        Double distance = cal.getOrthodromicDistance();

        return distance.longValue();
    }

    /**
     * Generate a circle around a given point
     *
     * @param lat Degrees of latitude between -90 and 90
     * @param lng Degrees of longitude between -180 and 180
     * @param radiusMeters Meters of radius
     * @return Geometry a 3d circle around a lat,lng point in SRID 4326
     */
    public static synchronized Geometry create3DCircle(double lat, double lng, double radiusMeters) {

        calc.setStartingGeographicPoint(lng, lat);
        final int SIDES = 32 + 16 * ((int)Math.ceil(radiusMeters / 80000) / 5);

        double baseAzimuth = 360.0 / SIDES;
        Coordinate coords[] = new Coordinate[SIDES+1];
        for( int i = 0; i < SIDES; i++){
            double azimuth = 180 - (i * baseAzimuth);
            calc.setDirection(azimuth, radiusMeters);
            Point2D point = calc.getDestinationGeographicPoint();
            coords[i] = new Coordinate(point.getX(), point.getY());
        }
        coords[SIDES] = coords[0];

        LinearRing ring = geometryFactory.createLinearRing( coords );
        Polygon polygon = geometryFactory.createPolygon( ring, null );
        polygon.setSRID(4326);
        return polygon;
    }

}
