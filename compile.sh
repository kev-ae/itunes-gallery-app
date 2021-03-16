#!/bin/bash -ex
mvn clean
mvn compile
checkstyle -c cs1302_checks.xml src/main/java/cs1302/gallery/*.java
mvn -e -Dprism.order=sw exec:java -Dexec.mainClass="cs1302.gallery.GalleryDriver"
