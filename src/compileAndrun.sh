clear
echo 'Compile'
javac *.java data/*.java scheduler/*.java

echo 'Run'
./test_results "java Client" -o $1 -c other/ -n
