@echo off

curl "http://localhost:3000/restaurants/get/1" | json_pp

pause