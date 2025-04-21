#!/bin/bash

# Clean build output
echo "🧹 Cleaning build output..."
rm -rf target
mkdir -p target

# Compile all Java source files into target/
echo "🛠️  Compiling Java source files..."
javac -source 8 -target 8 -d target $(find src -name "*.java") -Xlint:-options

# Create manifest file with Main-Class
echo "📝 Creating manifest file..."
echo "Main-Class: ChampionsArena" > target/MANIFEST.MF

# Create jar from compiled classes + manifest
echo "📦 Creating JAR file..."
jar cfm champions-arena.jar target/MANIFEST.MF -C target .

echo "✅ Build complete: champions-arena.jar"
