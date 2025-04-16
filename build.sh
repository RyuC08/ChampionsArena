#!/bin/bash

rm -rf target
mkdir -p target
javac -d target $(find src -name "*.java")
