## Compile client
javac -classpath .:lib/activation-1.1.1.jar:/lib/jaxb-impl-2.0.1.jar:lib/jaxb-api-2.2.jar src/*.java src/data/*.java

## Run client
java -classpath ../Comp3100/src/:lib/activation-1.1.1.jar:/lib/jaxb-impl-2.0.1.jar:lib/jaxb-api-2.2.jar Client
