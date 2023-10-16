@echo off

curl -X PUT -H "Content-Type: application/json" "http://localhost:3000/dining-reviews/admin/update/1?status=ACCEPTED" | json_pp

pause