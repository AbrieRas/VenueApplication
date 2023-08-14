@echo off

curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Restaurant1\",\"zipCode\":\"12345\",\"peanutAllergyScore\":4,\"eggAllergyScore\":4,\"dairyAllergyScore\":5}" "http://localhost:3000/restaurants/add" | json_pp

pause