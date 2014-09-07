#!/bin/sh
for f in `ls ./fuente_imagenes`
do
	neato -Teps ./fuente_imagenes/$f -o ${f%\.*}.eps
done
