mkdir src/main/resources/temp/$1
git clone $2 src/main/resources/temp/$1
cd src/main/resources/temp/$1
/Users/hebo/Workspace/Homework/se3/backend-codeanalysis/mvnw package