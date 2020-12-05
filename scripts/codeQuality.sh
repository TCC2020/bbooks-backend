set -e
npm install
npm run coverage-to-codacy
grade=$(curl -s -X GET https://api.codacy.com/2.0/project \
  -H 'Accept: application/json' \
  -H 'project_token: &CODACY_PROJECT_TOKEN' | jq -r '.commit.commit.grade')
echo $grade
if [[ "$grade" == "A" ]]
  then
    echo -e "\e[1;42m Grade $grade is ok. Build will continue. \e[0m"
  else
    echo -e "\e[1;31m Grade $grade is below the expected and the build will stop. \e[0m"
    exit 1
fi