@echo off

curl -X POST -H "Content-Type: application/json" -d "{\"reviewer\": \"John Doe\", \"restaurantId\": 1, \"peanutAllergyScore\": 4, \"eggAllergyScore\": 3, \"dairyAllergyScore\": 5, \"commentary\": \"Great dining experience!\", \"status\": \"RECEIVED\"}" "http://localhost:3000/dining-reviews/add" | json_pp

pause