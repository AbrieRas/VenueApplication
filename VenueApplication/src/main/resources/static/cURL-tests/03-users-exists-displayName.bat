@echo off

curl "http://localhost:3000/users/exists/John%20Doe" | json_pp

pause