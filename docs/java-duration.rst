.. _rstjavaduration:

###############
Duration (Java)
###############

The :ref:`rstage` messages use `ISO8601 duration <https://en.wikipedia.org/wiki/ISO_8601#Durations>`_ strings. These
can be easily converted to Java types using the Period class.


.. code-block:: java

  import java.time.Period;

  Subject subject = phenopacket.getSubject();
  if (subject.hasAgeAtCollection()) {
      // Phenopacket Age
      Age ageAtCollection = subject.getAgeAtCollection();
      // Java Period
      Period agePeriod = Period.parse(ageAtCollection.getAge());
  }

