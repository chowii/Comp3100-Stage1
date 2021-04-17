#!/bin/sh
#
#THE_CLASSPATH=
#PROGRAM_NAME=Client.java
##cd src
#for i in `ls ../lib/*.jar`
#    do
#    THE_CLASSPATH=${THE_CLASSPATH}:${i}
#done
#
#javac -classpath ".:${THE_CLASSPATH}" $PROGRAM_NAME
#
#if [ $? -eq 0 ]
#then
#    echo "compile worked!"
#fi
#
#/usr/lib/jvm/java-1.11.0-openjdk-amd64/bin/java -javaagent:/home/sabbib/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/203.7148.57/lib/idea_rt.jar=42865:/home/sabbib/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/203.7148.57/bin -Dfile.encoding=UTF-8 -classpath /home/sabbib/IdeaProjects/Comp3100/out/production/Comp3100-Stage1:/home/sabbib/IdeaProjects/Comp3100/lib/activation-1.1.1.jar:/home/sabbib/IdeaProjects/Comp3100/lib/jaxb-impl-2.0.1.jar:/home/sabbib/IdeaProjects/Comp3100/lib/jaxb-api-2.2.jar Client

echo Removing compiled class files
rm `pwd`/src/*.class
echo Removed compiled class files
sleep 1
echo Copying compiled class files
cp `pwd`/out/production/Comp3100-Stage1/data/*.class `pwd`/src
cp `pwd`/out/production/Comp3100-Stage1/*.class `pwd`/src
echo Copied compiled class files
