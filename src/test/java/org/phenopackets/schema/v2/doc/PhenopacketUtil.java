package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.phenopackets.schema.v1.core.GestationalAge;
import org.phenopackets.schema.v1.core.OntologyClass;
import org.phenopackets.schema.v1.core.TimeElement;
import org.phenopackets.schema.v1.core.VitalStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class PhenopacketUtil {


    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder().setId(id).setLabel(label).build();
    }


    public static TimeElement timeElementFromDateString(String timeStampString) throws ParseException {
        Timestamp t = Timestamps.parse(timeStampString);
        return TimeElement.newBuilder().setTimestamp(t).build();
    }

    public static VitalStatus vitalStatusDeceased(OntologyClass oclass, TimeElement timeElement) {
        return VitalStatus
                .newBuilder()
                .setCauseOfDeath(oclass)
                .setTimeOfDeath(timeElement)
                .setStatus(VitalStatus.Status.DECEASED).build();
    }

    public static VitalStatus vitalStatusAlive() {
        return VitalStatus
                .newBuilder()
                .setStatus(VitalStatus.Status.ALIVE).build();
    }

    public static GestationalAge gestationalAge(int weeks, int days) {
        if (weeks <0) {
            throw new RuntimeException("Gestational age weeks must be a non-negative integer");
        }
        if (weeks > 45) {
            throw new RuntimeException("Unrealistic gestational week (usually less than 43). Use Builder if needed");
        }
        if (days < 0) {
            throw new RuntimeException("Gestational age days must be a non-negative integer");
        }
        if (days > 6) {
            throw new RuntimeException("Gestational age days must less than 7");
        }
        return GestationalAge.newBuilder().setWeeks(weeks).setDays(days).build();
    }

}
