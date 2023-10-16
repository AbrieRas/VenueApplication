@echo off

curl -X PUT -H "Content-Type: application/json" -d "{\"displayName\": \"John Doe\",\"city\": \"Updated Sample City\",\"state\": \"Updated Sample State\",\"zipCode\": \"NEW 12345\",\"peanutAllergyInterest\": \"true\",\"eggAllergyInterest\": \"false\",\"dairyAllergyInterest\": \"true\"}" "http://localhost:3000/users/update/1" | json_pp

pause