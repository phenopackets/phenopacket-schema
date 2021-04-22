.. _rstdrugtype:

#########
DrugType
#########


Drugs can be administered in different contexts. This element does not intend to capture information about
the administration route (e.g., by mouth or intravenous), but rather about the setting - inpatient, outpatient,
or related to a (generally one-time) procedure.


DrugType is an enumeration.



.. list-table:: Values  of the ``DrugType`` element
   :widths: 50 50
   :header-rows: 1

   * - Item
     - Value
   * - UNKNOWN_DRUG_TYPE
     - 0
   * - PRESCRIPTION
     - 1
   * - EHR_MEDICATION_LIST
     - 2
   * - ADMINISTRATION_RELATED_TO_PROCEDURE
     - 3




This element does not intend to capture the medical reason for administering
a treatment. Instead, it records the context in which the medication is
administered. It is assumed that medications recorded on
the medication list of an electronic health record (EHR) are likely
to have been actually administered as prescribed. If a prescription
is given for outpatient use, it is less likely that the medication
will be taken exactly as prescribed
(`Osterberg L, Adherence to medication. N Engl J Med. 2005 <https://pubmed.ncbi.nlm.nih.gov/16079372/>`_).
Finally, medications may be given to conduct a medical procedure such as
a local anesthetic given before a skin biopsy or a sedative given to perform
a bronchoscopy.


