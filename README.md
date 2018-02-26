# catvsdog-resultApp
A Java application which consume the result app API

run with env $(cat local.env) docker-compose up

#Connect to database
psql -h ec2-18-219-4-201.us-east-2.compute.amazonaws.com -p 5433 -U admin -W result
psql -h localhost -p 5433 -U admin -W result