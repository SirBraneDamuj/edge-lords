cd web
rm -rf build
npm install
npm run build
cd ../server
./gradlew shadowJar
cd ..
