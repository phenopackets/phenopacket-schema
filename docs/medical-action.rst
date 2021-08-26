.. _rstmedicalaction:

##############
MedicalAction
##############

This element describes medications, procedures, other actions taken
for clinical management. The element is a list of options.


Data model
##########

.. list-table:: Definition  of the ``MedicalAction`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - action
     - one of {:ref:`rstprocedure` | :ref:`rsttreatment` | :ref:`rstradiotherapy` | :ref:`rsttherapeuticregimen`}
     - 1..1
     - One of a list of medical actions. REQUIRED.
   * - treatment_target
     - :ref:`rstontologyclass`
     - 0..1
     - The condition or disease that this treatment was intended to address
   * - treatment_intent
     - :ref:`rstontologyclass`
     - 0..1
     - Whether the intention of the treatment was curative, palliative...
   * - response_to_treatment
     - :ref:`rstontologyclass`
     - 0..1
     - How the patient responded to the treatment
   * - adverse_events
     - :ref:`rstontologyclass` (List)
     - 0..*
     - Any adverse effects experienced by the patient attributed to the treatment
   * - treatment_termination_reason
     - :ref:`rstontologyclass`
     - 0..1
     - The reason that the treatment was stopped.


action
~~~~~~

Each MedicalAction element refers to one of the following specific types of medical action:

* :ref:`rstprocedure`
* :ref:`rsttreatment`
* :ref:`rstradiotherapy`
* :ref:`rsttherapeuticregimen`





