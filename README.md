
# WordStats Simple

Simple demonstrative Java CLI App for analysing word lengths in a text.

A word is a string of alphanumeric characters, ampersand or forward slashes.

## Running

### Run with maven

To run, build with maven and run the resulting Jar:
```bash
mvn install
java -jar $(ls  target/*.jar | head -n 1) <filepath>
```

### Run with Docker

```bash
docker build -t wordstats-simple  .
cat <filepath> |  docker run --rm -i wordstats-simple
```