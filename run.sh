#!/bin/bash

# Ensure the script exits on error
set -e

# Run the ChampionsArena main class from the compiled target directory
java -cp target ChampionsArena console
