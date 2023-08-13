@echo off

curl -X POST -H "Content-Type: application/json" -d "{\"displayName\":\"abc\",\"city\":\"city123\",\"state\":\"state123\",\"zipCode\":\"zipcode123\",\"peanutAllergyInterest\":\"false\",\"eggAllergyInterest\":\"true\",\"dairyAllergyInterest\":\"false\"}" "http://localhost:3000/users/add" | json_pp

pause