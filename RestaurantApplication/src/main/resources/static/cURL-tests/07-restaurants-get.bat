@echo off

curl "http://localhost:3000/restaurants/get?zipCode=12345&allergyType=peanut" | json_pp
curl "http://localhost:3000/restaurants/get?zipCode=12345&allergyType=egg" | json_pp
curl "http://localhost:3000/restaurants/get?zipCode=12345&allergyType=dairy" | json_pp

pause