#!/bin/sh

# removing all and reinstall docker-compose.yml

echo "Deploying not-bug backend..."
image_name="tools.ats-online.it:5000/0lf.supervisor-backend:edge"
component_name="docker-compose_app-server_"
yml_path="docker-compose"
yml_path_filename="docker-compose.yml"

ssh -i "~/ssl/not-bug-cert.pem" -t root@66.97.43.52 "docker stop $component_name && docker rm $component_name && docker rmi $image_name  && cd $yml_path/ && /opt/bin/docker-compose -f $yml_path_filename up -d --build"
echo "Deploy success"
