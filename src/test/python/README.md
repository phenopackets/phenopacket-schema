Phenopackets Python Example
===========================

Assuming this file is located in the directory `~/github/phenopacket-schema/src/test/python`

```bash
$ phenopacket_srcdir="~/github/phenopacket-schema"
$ cd $phenopacket_srcdir
# build the project using maven (requires Java)
$ ./mvnw package
# alternatively read-up on protoc
# now setup a dir for the python virtual environment
$ venv_dir="~/python_envs/phenopacket-schema"
$ mkdir -p $venv_dir
$ cd $venv_dir
# copy the auto-generated python files to the env
$ cp -r $phenopacket_srcdir/target/generated-sources/protobuf/python/* .
# copy the test project files 
$ cp $phenopacket_srcdir/src/test/python/* .
# setup the python virtual env at `venv` in the current folder
$ python3 -m venv venv
$ source venv/bin/activate
$ pip install -r $phenopacket_srcdir/requirements.txt
$ python phenopackets_test.py
```

> the last command in the script above fails. **TODO**: resolve. 