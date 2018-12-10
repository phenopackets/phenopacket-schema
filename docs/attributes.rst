==================
Attributes element
==================

Attributes are encoded using three messages and make use of an enum that represents a null value.::
  
  enum NullValue {
    // Null value.
    NULL_VALUE = 0;
    }

AttributeValue
==============
The element defines a collection of attributes associated with various protocol
records. Each attribute is a name that maps to an array of one or more
values. Values are chosen from both internal protobuf types and GA4GH.
Values should be split into array elements instead of using a separator
syntax that needs to parsed.::

  message AttributeValue {
    oneof value {
        string string_value = 1;
        int64 int64_value = 2;
        int32 int32_value = 3;
        bool bool_value = 4;
        double double_value = 5;
        ExternalReference external_reference = 6;
        OntologyClass ontology_class = 7;
        Experiment experiment = 8;
        //Program is missing a definition in the original metadaa-schemas
        //        Program program = 9;
        Analysis analysis = 10;
        NullValue null_value = 11;
        Attributes attributes = 12;
        AttributeValues attribute_values = 13;
    }
  }


ToDOo--provide examples of intended usa cases.  

ToDo-- define when to use map vs list.::

  message AttributeValues {
    repeated AttributeValue values = 1;
  }
  
  message Attributes {
    map<string, AttributeValues>
  }

TODO.

