Cancer Phenopacket Example Data
=
This file has been transcribed into markdown from the original file which can be found [here](https://docs.google.com/document/d/1fM-bucd3wKqmxa4Id2DCOdvHDCCueTHm4Tz2wQxY7M8).


Cancer example
==

male

age at diagnosis: P48Y3M

date of birth: 1964-03-15

patient origin: Beppu, Japan

HPV-18 positive (cancer tissue)

diagnosis: Squamous cell carcinoma of the esophagus, T2N1M0

diagnostic sample (tumor resection) => variants, SNV (WES) & CNV (array) ; analysed in Cambridge, MA, U.S.

Biosample (lymph node biopsy) => variants, SNV (WES) & CNV (array) ; analysed in Cambridge, MA, U.S.

Intervention: surgery & radiation

Recurrence
===
time to recurrence: P11M

recurrence sites: local, lung metastasis

recurrence sample, esophagus (biopsy): => variants, SNV & CNV (WGS) ; analysed in Cambridge, MA, U.S.

Intervention:
=== 
 radiation w/ palliative intent

time to death from diagnosis: P2Y4M

metastasis sample, lung (autopsy): => variants, SNV & CNV (WGS);  ; analysed in Cambridge, MA, U.S.


Example codes:
===
Diagnoses: 
(icdom = ICD-O 3 Morphology; icdot = ICD-O Topography); 

```json
{ 
 "bio_ontologies" :
  {
    "seer" : { "term_label": "Esophagus", "term_id": "seer:21010" },
    "icdot" : { "term_label": "esophagus", "term_id": "icdot:c15.9" },
    "icdom" : { "term_label": "Squamous cell carcinoma, NOS", "term_id": "icdom:8070_3"},
    "ncit" : { "term_label": "Esophageal Squamous Cell Carcinoma", "term_id": "ncit:c4024" }
  }
 }
```
Variants (modified from GA4GH style annotation in arrayMap):
CNV/CNA and SNV example:

```json
{
  "Variants" : [
    {
      "variantset_id" : "tcga_ga4gh_vs_GRCh38",
      "callset_id" : "14b7d726-410a-4847-83d0-ba01ffc3336c_6b99c1ba-83d1-4636-bb8d-b1dccaca6afe",
      "digest" : "8:116618580-145078636:DUP",
      "reference_name" : "8",
      "start" : 116618580,
      "cipos" : [-500, 500],
      "end" : 145078636,
      "ciend" : [-500, 500],
      "variant_type" : "DUP",
      "genotype" : [],
      "alternate_bases" : null,
      "reference_bases" : ".",
      "variantset_id" : "arraymap_vs_GRCh38",
      "info" : {
        "svlen" : 28460056,
        "experiment_type" : "GPL6801"
      }
    },
        {
          "variantset_id" : "tcga_ga4gh_vs_GRCh38",
          "callset_id" : "14b7d726-410a-4847-83d0-ba01ffc3336c_6b99c1ba-83d1-4636-bb8d-b1dccaca6afe",
          "digest" : "1:43447122-43447122:C=>T",
          "reference_name" : "1",
          "start" : 43447122,
          "end" : 43447122,
          "reference_bases" : "C",
          "alternate_bases" : [
            "T"
          ],
          "genotype" : [ 0, 1],
          "updated" : "2018-03-05T08:45:09Z",
          "info" : {
            "experiment_type" : "WES"
          }
        }
    ]
}

```
Other:
```json
{
    "geo_data" : {
        "geo_precision" : "city",
        "geo_json" : {
        "type" : "Point",
        "coordinates" : [
          131.5,
          33.28
        ]
        },
        "info" : {
            "city" : "Beppu",
            "country" : "Japan",
            "continent" : "Asia"
        },
        "geo_label" : "Beppu, Japan, Asia"
    },
    "external_identifiers" : {
        "pubmed" : {
        "relation" : "denotes",
        "identifier" : "pubmed:17470683"
        }
    },
    "attributes" : {
      "tumor_stage" : "stage iib"
    },
    "updated" : "2018-03-06T13:14:15Z",
    "individual_age_at_collection" : "P48Y3M"
}
```