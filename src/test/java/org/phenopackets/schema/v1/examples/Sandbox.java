package org.phenopackets.schema.v1.examples;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sandbox {



    @Test
    void testTimeStamp() {
        long millis  = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();

    }


    @Test
    void testSpecificData() throws ParseException {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String hastings = "1066-10-14";
        Date date = formatter.parse(hastings);
        long millis = date.getTime();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        System.out.println(timestamp);
    }
}
