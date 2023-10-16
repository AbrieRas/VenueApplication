@echo off

curl "http://localhost:3000/dining-reviews/admin/get-received-reviews" | json_pp

pause