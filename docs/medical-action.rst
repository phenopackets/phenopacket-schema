.. _rstmedicalaction:

==============
Medical action
==============

This element describes medications, procedures, other actions taken
for clinical management. The element is a list of options.


**Data model**


.. list-table:: Definition  of the ``MedicalAction`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - action
     - one of :ref:`rstprocedure`, :ref:`rsttreatment`, :ref:`rstchemotherapy`, :ref:`rsthormonetherapy`,:ref:`rstradiotherapy`.
     - required
     - One of a list of medical actions

.. code-block:: json

    message MedicalAction {
    oneof action {
        Procedure procedure = 1;
        Treatment treatment = 2;
        ChemoTherapyTreatment chemo_therapy = 3;
        HormoneTherapyTreatment hormone_therapy = 4;
        RadiationTherapyTreatment radiation_therapy = 5;
    }
}



treatment
~~~~~~~~~

The element would be for treatments that are not pharmaceutical or surgical,
for instance dialysis, oxygen therapy, phototherapy,
or some other kind of therapy administering a quantity of an agent of a course of time.


To be discussed. Is this the optimal way of modeling an ``other`` category?


pharmaceutical_treatment
~~~~~~~~~~~~~~~~~~~~~~~~

See :ref:`rstpharmaceuticaltreatment`

radiotherapy_treatment
~~~~~~~~~~~~~~~~~~~~~~

This element would model Radiation therapy (also called radiotherapy).

procedure
~~~~~~~~~
This element models surgery, biopsy, intubation, counseling.

See :ref:`rstprocedure`.




