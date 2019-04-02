package com.robert.dd.doordashserver.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.geo.Point;

import java.io.IOException;

public class PointSerializer extends StdSerializer<Point> {

        public PointSerializer() {
            this(null);
        }

        public PointSerializer(Class<Point> t) {
            super(t);
        }

        @Override
        public void serialize(
                Point value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("latitude",  String.valueOf(value.getX()));
        jgen.writeStringField("longitude", String.valueOf(value.getY()));
        jgen.writeEndObject();
        }
}
