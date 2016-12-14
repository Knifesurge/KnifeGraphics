#!/bin/bash/
javac -cp ".:bin/:src/com/knifesurge/KnifeGraphics/:src/com/knifesurge/testing:/home/knifesurge/Documents/lwjgl-3.0.0/jar/lwjgl.jar" src/com/knifesurge/KnifeGraphics/*.java src/com/knifesurge/testing/*.java -d bin
echo "Done"
