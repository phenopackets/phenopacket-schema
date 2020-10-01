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
     - one of Procedure, Treatment, Chemotherapy, Hormonetherapy, or Radiotherapy.
     - required
     - One of a list of medical actions


action
~~~~~~

Each MedicalAction element refers to one of the following specific types of medical action:

* :ref:`rstprocedure`
* :ref:`rsttreatment`
* :ref:`rstchemotherapy`
* :ref:`rsthormonetherapy`
* :ref:`rstradiotherapy`





