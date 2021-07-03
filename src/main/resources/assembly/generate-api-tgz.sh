#!/bin/sh
 
version="$1"

pwd

echo "Unzip mtsuite-api-v"$version".tgz"
rm -rf mtsuite-api-v$version
mkdir mtsuite-api-v$version
tar -xvzf mtsuite-api-v$version.tgz -C mtsuite-api-v$version

echo "Removing mtsuite-api-v"$version".tgz"
rm -rf mtsuite-api-v$version.tgz

echo "Compressing mtsuite-api-v"$version
tar -czvf mtsuite-api-v$version.tgz mtsuite-api-v$version
rm -rf mtsuite-api-v$version

echo "Success -> mtsuite-api-v"$version".tgz created"
