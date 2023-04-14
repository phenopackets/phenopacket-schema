extern crate prost_build;

use std::env;
use std::path::PathBuf;

fn main() -> std::io::Result<()> {
    // build protoc from source and set as an env variable
    env::set_var("PROTOC", protobuf_src::protoc());

    let descriptor_path = PathBuf::from(env::var("OUT_DIR").unwrap())
        .join("proto_descriptor.bin");

    // only requires top-level protos as prost will automatically find the other imports
    let proto_files = [
        "ga4gh/pedigree/v1/pedigree.proto",
        "phenopackets/schema/v1/phenopackets.proto",
        "phenopackets/schema/v2/phenopackets.proto",
    ];


    prost_build::Config::new()
        // Save descriptors to file
        .file_descriptor_set_path(&descriptor_path)
        // Override prost-types with pbjson-types
        .compile_well_known_types()
        .extern_path(".google.protobuf", "::pbjson_types")
        .include_file("mod.rs")
        // don't derive standard serde implementations as this will not be compatible with the
        // canonical protobuf json, instead use the pbjson lib. This happens in the next step
        // .type_attribute(".", "#[derive(serde::Serialize,serde::Deserialize)]")
        .protoc_arg("--proto_path=src/main/proto/")
        .compile_protos(&proto_files, &["src/main/rust"])?;

    let descriptor_set = std::fs::read(descriptor_path)?;
    // build protobuf json-compatible Serde implementations
    pbjson_build::Builder::new()
        .register_descriptors(&descriptor_set)?
        .build(&[".org"])?;

    Ok(())
}
