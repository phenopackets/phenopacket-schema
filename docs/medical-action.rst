.. _rstmedicalaction:

==============
Medical action
==============

This element describes medications, procedures, other actions taken
for clinical management. The element is a list of options.

.. code-block:: json

    message MedicalAction {
        oneof action {
            // e.g.
            Treatment treatment = 1; // to be discussed
            PharmaceuticalTreatment pharmaceutical_treatment = 2;
            RadiotherapyTreatment radiotherapy_treatment = 3;
            Procedure procedure = 4;
            // (to be expanded....)
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




