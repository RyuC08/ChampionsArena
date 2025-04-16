#!/bin/bash

mkdir -p target
javac -d target $(find src -name "*.java")
