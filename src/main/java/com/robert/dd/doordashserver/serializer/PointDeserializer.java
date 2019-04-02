package com.robert.dd.doordashserver.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.data.geo.Point;

import java.io.IOException;

public class PointDeserializer extends StdDeserializer<Point> {

    public PointDeserializer(){
        this(null);
    }
    public PointDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Point deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // try catch block
        try {
            Double lat = Double.valueOf(node.get("latitude").asText());
            Double lon = Double.valueOf(node.get("longitude").asText());
            Point p = new Point(lat, lon);
            return p;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
