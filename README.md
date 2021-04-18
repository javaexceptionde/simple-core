# SimpleCore
Diese Core ist ein Projekt welches die Entwicklung von Minecraft Plugins erleichtern soll sie enthält einige kleinere und größere selbst geschriebene API's beziehungsweise fungiert als eine Große API.

## Installation
### Maven
```
<repositories>
    <repository>
        <id>corev-repo</id>
        <url>https://repo.legendempires.de/repository/maven-releases/</url>
    </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>dev.jbull</groupId>
    <artifactId>core-api</artifactId>
    <version>1.0-Beta</version>
  </dependency>
</dependencies>
```
### Gradle
```
repositories {
    maven {
        url("https://repo.legendempires.de/repository/maven-releases/")
    }
}

dependencies {
  implementation 'dev.jbull:core-api:1.0-Beta@jar'
}
``` 
Um die API wirkungsvoll nutzen zu können muss auf dem Server auch das [Core Plugin](https://www.spigotmc.org/resources/the-simple-core.91442) installiert sein. 
Die Dokumentation der API findest du [hier](https://docs.jbull.dev/)
