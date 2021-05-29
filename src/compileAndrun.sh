echo 'Compile'
javac -classpath `pwd`:`pwd`/../lib/activation-1.1.1.jar:`pwd`/../lib/jaxb-api-2.3.1.jar:`pwd`/../lib/org.eclipse.persistence.moxy-2.7.3.jar:`pwd`/../lib/jaxb-api-2.2.jar:`pwd`/../lib/jaxb-impl-2.0.1.jar *.java data/*.java scheduler/*.java

echo 'Run'
./test_results "java -cp `pwd`:`pwd`/../lib/activation-1.1.1.jar:`pwd`/../lib/jaxb-api-2.3.1.jar:`pwd`/../lib/org.eclipse.persistence.moxy-2.7.3.jar:`pwd`/../lib/jaxb-api-2.2.jar:`pwd`/../lib/jaxb-impl-2.0.1.jar Client" -o $1 -c other/ -n
