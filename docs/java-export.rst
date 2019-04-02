.. _rstjavaexport:

====================================
Exporting and Importing Phenopackets
====================================

It is easy to export Phenopackets in JSON, YAML, or protobuf format.


JSON export
~~~~~~~~~~~
In many situations it
may be desirable to export the Phenopacket as `JSON <https://en.wikipedia.org/wiki/JSON>`_. This is easy with
the following commands (we show how to create a Phenopacket in Java elsewhere).

.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import com.google.protobuf.util.JsonFormat;
    import java.io.IOException;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        System.out.println(toJson(phenoPacket));
        System.out.println(JsonFormat.printer().includingDefaultValueFields().print(pp));
     } catch (IOException e) {
       e.printStackTrace();
     }



YAML export
~~~~~~~~~~~
`YAML <https://yaml.org/>`_ (YAML Ain't Markup Language) is a human friendly data serialization
  standard for all programming languages.

.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import com.google.protobuf.util.JsonFormat;
    import java.io.IOException;
    import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        String JSONString = JsonFormat.printer().includingDefaultValueFields().print(phenoPacket);
        JsonNode jsonNodeTree = new ObjectMapper().readTree(JSONString);
        String yamlString = new YAMLMapper().writeValueAsString(jsonNodeTree);
        System.out.println(yamlString);
    } catch (IOException e) {
        e.printStackTrace(); // or handle the Exception as appropriate
    }


Protobuf export
~~~~~~~~~~~~~~~
For most use case, we recommend using JSON as the serialization format for Phenopackets. Protobuf
is more space efficient than JSON but it is a binary format that is not human readable.


.. code-block:: java

    import org.phenopackets.schema.v1.Phenopacket;
    import java.io.IOException;

    Phenopacket phenoPacket = // create a Phenopacket
    try {
        phenoPacket.writeTo(System.out);
     } catch (IOException e) {
        e.printStackTrace(); // or handle the Exception as appropriate
    }

We can write to any FileOutputStream (replace System.out in the above code).


Importing Phenopackets (JSON format)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
There are multiple ways of doing this with different JSON libraries. The following code should suffice for most
purposes.

.. code-block:: java

    import org.json.simple.JSONObject;
    import org.json.simple.parser.JSONParser;
    import org.json.simple.parser.ParseException;

    String path = // path to the phenopacket file in JSON format
    JSONParser parser = new JSONParser();
    try {
        Object obj = parser.parse(new FileReader(pathToJsonPhenopacketFile));
        JSONObject jsonObject = (JSONObject) obj;
        String phenopacketJsonString = jsonObject.toJSONString();
        Phenopacket.Builder phenoPacketBuilder = Phenopacket.newBuilder();
        JsonFormat.parser().merge(jsonString, phenoPacketBuilder);
        Phenopacket phenopacket = phenoPacketBuilder.build();
        // do somethign with phenopacket ...
    } catch (IOException e1) {
        e1.printStackTrace(); // or handle the Exception as appropriate
    } catch (ParseException e2) {
        e2.printStackTrace(); // or handle the Exception as appropriate
    }