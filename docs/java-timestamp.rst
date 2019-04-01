.. _rstjavatimestamp:

=========
Timestamp
=========

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
    import java.text.SimpleDateFormat;
    import java.util.Date;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String hastings = "1066-10-14";
    Date date = formatter.parse(hastings);
    long millis = date.getTime();
    Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
              .setNanos((int) ((millis % 1000) * 1000000)).build();

If more precision is desired, use the following format

.. code-block:: java

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");