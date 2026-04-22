# `gitrpt` — Git Report

## Develop

Make sure Java is available
```shell
java -version
```

Install Clojure
```shell
brew install clojure/tools/clojure
```

Run directly
```shell
clj -M -m com.github.nalaka.gitrpt.main
```

Build uber jar
```shell
clj -T:build uber
```

Rub uber jar
```shell
java -jar target/gitrpt-0.1.0-standalone.jar
```
