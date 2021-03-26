.. _rsttimeinterval:

************
TimeInterval
************

An time interval is meant to denote an interval of time whose begin and end is defined
by `Timestamps <https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/Timestamp>`_.




Data model
##########


.. list-table:: Definition  of the ``TimeInterval`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - start
     - Timestamp
     - required
     - begin of interval
   * - end
     - Timestamp
     - required
     - end of interval


Example
#######

The following message could be used to represent the
interval from March 15, 2020, 1PM to March 25, 2020, 9PM.

.. code-block:: yaml

  timeInterval:
    start: "2020-03-15T13:00:00Z"
    end: "2020-03-25T09:00:00Z"


Explanations
############


start
~~~~~
The date and time of the start of the interval.

end
~~~
The date and time of the end of the interval.


**Privacy concerns**

In some cases it may be desirable to shift all specific dates in a phenopacket by the same random amount. For instance, we
might shift all dates by 2 years. In this case the above interval element would be represented as follows

.. code-block:: yaml

  timeInterval:
    start: "2018-03-15T13:00:00Z"
    end: "2018-03-25T09:00:00Z"