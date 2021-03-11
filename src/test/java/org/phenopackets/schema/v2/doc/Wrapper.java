package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
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

public class Wrapper {


    public static OntologyClass buildOntologyClass(String id, String label) {
        return OntologyClass.newBuilder().setId(id).setLabel(label).build();
    }


    public static TimeElement buildFromDateString(String timeStampString) throws ParseException {
        Timestamp t = Timestamps.parse(timeStampString);
        return TimeElement.newBuilder().setTimestamp(t).build();
    }

    public static VitalStatus buildDeceasedStatus(OntologyClass oclass, TimeElement timeElement) {
        return VitalStatus
                .newBuilder()
                .setCauseOfDeath(oclass)
                .setTimeOfDeath(timeElement)
                .setStatus(VitalStatus.Status.DECEASED).build();
    }

    public static VitalStatus buildAliveStatus() {
        return VitalStatus
                .newBuilder()
                .setStatus(VitalStatus.Status.ALIVE).build();
    }

}
