.. _rstcovid19example:

============================
A complete example: COVID-19
============================

Here, we demonstrate how phenopackets are used to represent the time course of
a case of severe COVID-19 based on a
`published case report <https://casereports.onlinejacc.org/content/early/2020/05/21/j.jaccas.2020.04.001>`_.
We will explain each of the components of the phenopacket in order.

subject
~~~~~~~

The subject is a 70 year-old male who died owing to complications of COVID-19. The
time and cause of death are represented by the :ref:`rstvitalstatus` element.
Note that the timestamp is with reference to the Unix time, defined as the
number of seconds that have elapsed since the Unix epoch, minus leap seconds;
the Unix epoch is 00:00:00 UTC on 1 January 1970. Thus, 1585353600 corresponds
to Saturday March 28, 2020 00:00:00 (AM).

.. code-block:: yaml

   subject:
      id: "P123542"
      sex: "MALE"
      timeAtEncounter:
        age:
          iso8601duration: "P70Y"
      vitalStatus:
        status: "DECEASED"
        timeOfDeath:
          timestamp: "2020-03-28T00:00:00Z"
        causeOfDeath:
          id: "MONDO:0100096"
          label: "COVID-19"

