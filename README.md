# MentionExtractor
[![Maven Central](https://img.shields.io/maven-central/v/io.github.brenoepics/MentionExtractor?color=purple)](https://central.sonatype.com/artifact/io.github.brenoepics/MentionExtractor)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_MentionExtractor&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brenoepics_MentionExtractor)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=brenoepics_MentionExtractor&metric=alert_status&)](https://sonarcloud.io/summary/new_code?id=brenoepics_MentionExtractor)

A Kotlin library for extracting mentions from a text based on specified patterns.
It provides flexible configuration options for handling mentions in a text.

## Usage

To use the `MentionExtractor` library:

1. Create a `MentionPattern` instance and configure it according to your requirements.

2. Use the `MentionExtractor.Builder` to construct an instance of `MentionExtractor`, providing the configured `MentionPattern` and any additional settings such as the maximum number of mentions to handle.

3. Use the methods provided by `MentionExtractor` to extract mentions from text, check if a text contains a mention, or count the number of mentions in a text.

Here's an example of how to use `MentionExtractor`:

```kotlin
// Configure MentionPattern
val mentionPattern = MentionPattern().withSpecialChars()

// Create MentionExtractor instance
val extractor = MentionExtractor.Builder()
    .pattern(mentionPattern)
    .maxMentions(5)
    .build()

// Extract mentions from text
val text = "Hey @user1, did you see @user2's message?"
val mentions = extractor.fromString(text)

// Check if text contains a mention
val containsMention = extractor.containsMention(text)

// Count the number of mentions in text
val mentionCount = extractor.countMentions(text)
```

## Download / Installation

The recommended way to get MentionExtractor is to use a build manager, like Gradle or Maven.

### [Maven-central](https://central.sonatype.com/artifact/io.github.brenoepics/MentionExtractor) Dependency
<details>
  <summary>Gradle</summary>
    
```gradle
implementation group: 'io.github.brenoepics', name: 'MentionExtractor', version: '1.0.0'
```
</details>
<details>
  <summary>Maven</summary>

```xml
<dependency>
    <groupId>io.github.brenoepics</groupId>
    <artifactId>MentionExtractor</artifactId>
    <version>1.0.0</version>
</dependency>
```
</details>
<details>
  <summary>Sbt</summary>

```sbt
libraryDependencies += "io.github.brenoepics" % "MentionExtractor" % "1.0.0"
```
</details>


## Testing

We use JUnit 5 for testing the `MentionExtractor`. To run the tests:

1. Ensure you have Maven installed on your system.

2. Navigate to the root directory of the project containing the `pom.xml` file.

3. Run the following Maven command:

   ```bash
   mvn test
   ```

This will execute the JUnit tests defined in the `test` package and provide the test results.

## License

This project is licensed under the Apache 2.0 License—see the [LICENSE](LICENSE) file for details.
