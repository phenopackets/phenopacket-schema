.. _rstjavatimestamp:

################
Timestamp (Java)
################

A Timestamp represents a point in time independent of any time zone or local
calendar, encoded as a count of seconds and fractions of seconds at
nanosecond resolution. The count is relative to an epoch at UTC midnight on
January 1, 1970, in the proleptic Gregorian calendar which extends the
Gregorian calendar backwards to year one
(see `Unix time <https://en.wikipedia.org/wiki/Unix_time>`_).


A timestamp is required for several elements of the Phenopacket including the
:ref:`rstmetadata`. Usually, code will create a timestamp to represent
the current time (the time at which the Phenopacket is being created).



.. code-block:: java

    import com.google.protobuf.Timestamp;

    long millis  = System.currentTimeMillis();
    Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();


It is also possible to create a timestamp for an arbitrary date. For instance, the following
code creates a timepoint for an important date in English history.


.. code-block:: java

    import com.google.protobuf.Timestamp;
    import java.time.Instant;
    import java.time.format.DateTimeFormatter;
    import java.time.temporal.TemporalAccessor;

    String hastings = "1066-10-14T00:00:00.001Z";
    TemporalAccessor date = DateTimeFormatter.ISO_DATE_TIME.parse(hastings);
    System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(date));
    Instant instant = Instant.from(date);
    Timestamp timestamp = Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();
    System.out.println(JsonFormat.printer().print(timestamp));