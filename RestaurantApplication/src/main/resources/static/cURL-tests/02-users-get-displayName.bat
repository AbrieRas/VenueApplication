@echo off

curl "http://localhost:3000/users/get/John%20Doe" | json_pp

pause