.. _rstinterval:

========
Interval
========

An interval is meant to denote an interval of time whose begin and end is defined
by `Timestamps <https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/Timestamp>`_.


.. code-block:: json

  message Interval {
        google.protobuf.Timestamp start = 1;
        google.protobuf.Timestamp end = 2;
  }

