@echo off

curl -X POST -H "Content-Type: application/json" -d "{\"displayName\": \"John Doe\",\"city\": \"Sample City\",\"state\": \"Sample State\",\"zipCode\": \"12345\",\"peanutAllergyInterest\": \"true\",\"eggAllergyInterest\": \"false\",\"dairyAllergyInterest\": \"true\"}" "http://localhost:3000/users/add" | json_pp

pause