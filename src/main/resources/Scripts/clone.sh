mkdir src/main/resources/temp/$1
mkdir src/main/resources/dependencies/$1
mkdir src/main/resources/sourceCode/$1
git clone $2 src/main/resources/temp/$1
cd src/main/resources/temp/$1
mvn package