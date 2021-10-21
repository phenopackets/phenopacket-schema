.. _rsttimestamp:

#########
Timestamp
#########

In phenopackets we define the `Timestamp` as an `ISO-8601 date time <https://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations>`_ string.

The following documentation paraphrases the description of how this is represented in protobuf as JSON `Timestamp <https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/Timestamp>`_

The format for this is "{year}-{month}-{day}T{hour}:{min}:{sec}[.{frac_sec}]Z" where {year} is always expressed using
four digits while {month}, {day}, {hour}, {min}, and {sec} are zero-padded to two digits each. The fractional seconds,
which can go up to 9 digits (i.e. up to 1 nanosecond resolution), are optional. The "Z" suffix indicates the timezone
("UTC"); the timezone is required.

For example, "2021-06-02T16:52:15.01Z" encodes 15.01 seconds past 16:52 UTC on June 2, 2021.

In JavaScript, one can convert a Date object to this format using the standard `toISOString() <https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/toISOString>`_ method.

In Python, a standard `datetime.datetime` object can be converted to this format using `strftime <https://docs.python.org/2/library/time.html#time.strftime>`_ with the time format spec '%Y-%m-%dT%H:%M:%S.%fZ'.

Likewise, in Java, one can use the `DateTimeFormatter.ISO_DATE_TIME <https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html#ISO_DATE_TIME>`_ to obtain a formatter capable of generating timestamps in this format.
