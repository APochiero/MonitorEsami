@echo off
c:\prg\jdk8\bin\javac -classpath c:\prg\libs\xstream-1.4.7.jar; .\monitorEsami\*.java
pause

set L=c:\prg\libs\
set LIBS=%L%xstream-1.4.7.jar;%L%xmlpull-1.1.3.1.jar;%L%xpp3_min-1.1.4c.jar;%L%mysql-connector-java-5.1.34-bin.jar;.\monitorEsami\;

start cmd /k  "C:\prg\jdk8\bin\java -classpath %LIBS% monitorEsami.ServerLogAttivita 8080"
start cmd /k  "C:\prg\jdk8\bin\java -classpath %LIBS% monitorEsami.MainClass"
pause
taskkill /f /im "java.exe"
pause