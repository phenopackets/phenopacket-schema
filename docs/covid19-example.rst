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
      timeAtLastEncounter:
        age:
          iso8601duration: "P70Y"
      vitalStatus:
        status: "DECEASED"
        timeOfDeath:
          timestamp: "2020-03-28T00:00:00Z"
        causeOfDeath:
          id: "MONDO:0100096"
          label: "COVID-19"


phenotypicFeatures
~~~~~~~~~~~~~~~~~~

The phenotypic features here describe the more qualitative phenotypic features the patient exhibited. He is described as
having an A+ blood group and suffering from a fever, flank pain, hematuria and later on myalgia, diarrhea and dyspnea
which develops into acute respiratory failure three days after the initial onset of symptoms at which point he is admitted
into hospital.

.. code-block:: yaml

    phenotypicFeatures:
    - type:
        id: "NCIT:C76246"
        label: "Blood Group A"
    - type:
        id: "NCIT:C76251"
        label: "Rh Positive Blood Group"
    - type:
        id: "NCIT:C3038"
        label: "Fever"
      onset:
        timestamp: "2020-03-17T00:00:00Z"
    - type:
        id: "NCIT:C34615"
        label: "Flank Pain"
      onset:
        timestamp: "2020-03-17T00:00:00Z"
    - type:
        id: "NCIT:C3090"
        label: "Hematuria"
      onset:
        timestamp: "2020-03-17T00:00:00Z"
    - type:
        id: "NCIT:C27009"
        label: "Myalgia"
      onset:
        interval:
          start: "2020-03-18T00:00:00Z"
          end: "2020-03-20T00:00:00Z"
    - type:
        id: "NCIT:C2987"
        label: "Diarrhea"
      onset:
        interval:
          start: "2020-03-18T00:00:00Z"
          end: "2020-03-20T00:00:00Z"
    - type:
        id: "NCIT:C2998"
        label: "Dyspnea"
      onset:
        interval:
          start: "2020-03-18T00:00:00Z"
          end: "2020-03-20T00:00:00Z"
    - type:
        id: "NCIT:C27043"
        label: "Acute Respiratory Failure"
      onset:
        timestamp: "2020-03-20T00:00:00Z"

measurements
~~~~~~~~~~~~

The measurements block allows quantitative recording of measurements as opposed to qualitative. For example 'Fever' (NCIT:C3038)
listed in the phenotypicFeatures section could be represented quantitatively as a :ref:`rstmeasurement` rather than a :ref:`rstphenotypicfeature`.

In this section we show two of the measurements for the absolute blood lymphocyte count listed in table 1 of the reference.
This shows the historical count from measurements taken within the six months prior to hospital admission, followed by the
count taken on admission.

.. code-block:: yaml

    measurements:
    - assay:
        id: "NCIT:C113237"
        label: "Absolute Blood Lymphocyte Count"
      value:
        quantity:
          unit:
            id: "NCIT:C67245"
            label: "Thousand Cells"
          value: 1.4
          referenceRange:
            unit:
              id: "NCIT:C67245"
              label: "Thousand Cells"
            low: 1.0
            high: 4.5
      timeObserved:
        interval:
          start: "2019-09-01T00:00:00Z"
          end: "2020-03-01T00:00:00Z"
    - assay:
        id: "NCIT:C113237"
        label: "Absolute Blood Lymphocyte Count"
      value:
        quantity:
          unit:
            id: "NCIT:C67245"
            label: "Thousand Cells"
          value: 0.7
          referenceRange:
            unit:
              id: "NCIT:C67245"
              label: "Thousand Cells"
            low: 1.0
            high: 4.5
      timeObserved:
        timestamp: "2020-03-20T00:00:00Z"


diseases
~~~~~~~~

This section displays a selection of the patient's prior medical history, without any timeframe indicating it included
ischemic cardiomyopathy, stage 3 chronic kidney disease and obesity. He did not have diabetes. He had a positive PCR test
for COVID-19 three days before admission to hospital.

.. code-block:: yaml

    diseases:
    - term:
        id: "NCIT:C2985"
        label: "Diabetes Mellitus"
      excluded: true
    - term:
        id: "NCIT:C34830"
        label: "Cardiomyopathy"
    - term:
        id: "NCIT:C80389"
        label: "Chronic Kidney Disease, Stage 3"
    - term:
        id: "NCIT:C3283"
        label: "Obesity"
    - term:
        id: "MONDO:0100096"
        label: "COVID-19"
      onset:
        timestamp: "2020-03-17T00:00:00Z"


medicalActions
~~~~~~~~~~~~~~

As post of his medical history it was noted he had previously had a left ventricular assist device implanted at some point
in 2016. Given this as not precisely specified a timestamp for Jan 1st 2016 is stated. Once admitted to hospital for
acute respiratory failure as a result of the COVID-19 infection the patient was administered nasal oxygen at 2L/min for
two days before requiring the dosage to be increased to 50L/min on day 2. Days 2 - 8, the patient required intubation
and oxygen was delivered via a positive end expiratory pressure valve device. He was treated with hydroxychloroquine
for the first 2 days before being administered Tocilizumab.

.. code-block:: yaml

    medicalActions:
    - procedure:
        code:
          id: "NCIT:C80473"
          label: "Left Ventricular Assist Device"
        performed:
          timestamp: "2016-01-01T00:00:00Z"
    - treatment:
        agent:
          id: "NCIT:C722"
          label: "Oxygen"
        routeOfAdministration:
          id: "NCIT:C38284"
          label: "Nasal Route of Administration"
        doseIntervals:
        - quantity:
            unit:
              id: "NCIT:C67388"
              label: "Liter per Minute"
            value: 2.0
          interval:
            start: "2020-03-20T00:00:00Z"
            end: "2020-03-22T00:00:00Z"
        - quantity:
            unit:
              id: "NCIT:C67388"
              label: "Liter per Minute"
            value: 50.0
          interval:
            start: "2020-03-22T00:00:00Z"
            end: "2020-03-23T00:00:00Z"
    - treatment:
        agent:
          id: "NCIT:C557"
          label: "Hydroxychloroquine"
        doseIntervals:
        - quantity:
            unit:
              id: "NCIT:C28253"
              label: "mg"
            value: 450.0
          scheduleFrequency:
            id: "NCIT:C64496"
            label: "Twice Daily"
          interval:
            start: "2020-03-20T00:00:00Z"
            end: "2020-03-20T00:00:00Z"
        - quantity:
            unit:
              id: "NCIT:C28253"
              label: "mg"
            value: 450.0
          scheduleFrequency:
            id: "NCIT:C125004"
            label: "Once Daily"
          interval:
            start: "2020-03-21T00:00:00Z"
            end: "2020-03-22T00:00:00Z"
    - procedure:
        code:
          id: "NCIT:C116648"
          label: "Tracheal Intubation"
        performed:
          timestamp: "2020-03-22T00:00:00Z"
    - treatment:
        agent:
          id: "NCIT:C722"
          label: "Oxygen"
        routeOfAdministration:
          id: "NCIT:C50254"
          label: "Positive end Expiratory Pressure Valve Device"
        doseIntervals:
        - quantity:
            unit:
              id: "NCIT:C91060"
              label: "Centimeters of Water"
            value: 14.0
          interval:
            start: "2020-03-22T00:00:00Z"
            end: "2020-03-28T00:00:00Z"
    - treatment:
        agent:
          id: "NCIT:C84217"
          label: "Tocilizumab"
        doseIntervals:
        - interval:
            start: "2020-03-24T00:00:00Z"
            end: "2020-03-28T00:00:00Z"


metaData
~~~~~~~~

The :ref:`rstmetadata` is required to provide details about all of the ontologies and external references used
in the Phenopacket.

.. code-block:: yaml

    metaData:
      resources:
      - id: "ncit"
        name: "NCI Thesaurus OBO Edition"
        url: "http://purl.obolibrary.org/obo/ncit.owl"
        version: "http://purl.obolibrary.org/obo/ncit/releases/2019-11-26/ncit.owl"
        namespacePrefix: "NCIT"
      - id: "mondo"
        name: "Mondo Disease Ontology"
        url: "http://purl.obolibrary.org/obo/mondo.obo"
        namespacePrefix: "MONDO"
      - id: "doi"
        name: "Digital Object Identifier"
        url: "http://dx.doi.org"
        namespacePrefix: "DOI"
      - id: "pubmed"
        name: "PubMed"
        url: "https://pubmed.ncbi.nlm.nih.gov/"
        namespacePrefix: "PUBMED"
      phenopacketSchemaVersion: "2.0"
      externalReferences:
      - id: "DOI:10.1016/j.jaccas.2020.04.001"
        reference: "PMID:32292915"
        description: "The Imperfect Cytokine Storm: Severe COVID-19 With ARDS in a Patient\
          \ on Durable LVAD Support"