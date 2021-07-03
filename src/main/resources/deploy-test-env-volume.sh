#!/bin/sh

# resync jar file and restart container

echo "Deploying not-bug backend..."
component_name="mtsuites-deploy_core-server_1"
jar_path="../../../target/tools.mtsuite.core-0.1.jar"
destination_path="/var/lib/docker/volumes/mtsuites-deploy_core-server_data/_data"

rsync -avhz --no-perms --no-owner --no-group --delete --partial -e "ssh -i ~/ssl/not-bug-cert.pem" $jar_path root@66.97.43.52:$destination_path
ssh -i "~/ssl/not-bug-cert.pem" -t root@66.97.43.52 "docker restart $component_name"
echo "Deploy success"
