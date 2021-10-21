.. _rsttherapeuticregimen:

##################
TherapeuticRegimen
##################

This element represents a therapeutic regimen which will involve a specified set of treatments for a particular condition.
It can be thought of as a shorthand for a more specific set of treatments. This element is supposed to reference a more
detailed regimen specification


Data model
##########

TherapeuticRegimen
~~~~~~~~~~~~~~~~~~

.. list-table:: Definition  of the ``TherapeuticRegimen`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - identifier
     - {:ref:`rstontologyclass` | :ref:`rstexternalreference`}
     - 1..1
     - An identifier for the regimen. REQUIRED.
   * - start_time
     - :ref:`rsttimeelement`
     - 0..1
     - When the regimen was started. RECOMMENDED.
   * - end_time
     - :ref:`rsttimeelement`
     - 0..1
     - When the regimen ended. An empty `end_time` with a populated `start_time` would indicate the regimen was ongoing. RECOMMENDED.
   * - status
     - :ref:`rstregimenstatus`
     - 1..1
     - Current status of the regimen for the enclosing :ref:`rstmedicalaction` on the :ref:`rstindividual`. REQUIRED.


.. _rstregimenstatus:

RegimenStatus
~~~~~~~~~~~~~
.. csv-table:: Definition  of the ``RegimenStatus`` enumeration
   :header: Name, Ordinal, Description

    UNKNOWN_STATUS, 0, The status of the regimen is unknown
    STARTED, 1,  The regimen was started
    COMPLETED, 2, The regimen was completed
    DISCONTINUED, 3, The regimen was discontinued but not completed


Example
#######

The following example describes twice daily dosing of 30 mg of losartan given orally.

.. code-block:: yaml

    therapeuticRegimen:
      externalReference:
        id: "NCT04576091"
        reference: "https://clinicaltrials.gov/ct2/show/NCT04576091"
        description: "Testing the Addition of an Anti-cancer Drug, BAY1895344, With Radiation\
          \ Therapy to the Usual Pembrolizumab Treatment for Recurrent Head and Neck Cancer"
      startTime:
        timestamp: "2020-03-15T13:00:00Z"
      regimenStatus: "STARTED"


Explanations
############

identifier
~~~~~~~~~~
An :ref:`rstontologyclass` or :ref:`externalreference` representing the therapeutic regimen which the `subject`
(:ref:`rstindividual`) has followed.


start_time
~~~~~~~~~~
When the regimen was started, as represented by a :ref:`rsttimeelement`.

end_time
~~~~~~~~
When the regimen ended, as represented by a :ref:`rsttimeelement`.

regimen_status
~~~~~~~~~~~~~~
The status of the regimen - whether it has started, completed or was discontinued. Regimens which were discontinued are
RECOMMENDED to record any adverse events (:ref:`rstmedicalaction`.adverse_events) and the reason for termination
(:ref:`rstmedicalaction`.treatment_termination_reason) in the enclosing :ref:`rstmedicalaction` message.


