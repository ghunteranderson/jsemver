# JSemVer
[![Build Status](https://travis-ci.com/ghunteranderson/jsemver.svg?branch=master)](https://travis-ci.com/ghunteranderson/jsemver)
[![Coverage Status](https://coveralls.io/repos/github/ghunteranderson/jsemver/badge.svg?branch=master)](https://coveralls.io/github/ghunteranderson/jsemver?branch=master)   


JSemVer is a Java based parser and comparator implementing the Semantic Versioning 2.0.0.
It also includes support for NPM like version range selectors.

## Semantic Versioning
The semantic versioning specification at semver.org is a widely used syntax and process
for managing your projects versions in a semantically meaningful way. Semantic versions
pack a lot of information into a small string but programatically accessing that data
is not always so easy.

### Parsing

``` java
Version v = Version.from("1.2.3-alpha.12+build.043");
int major = v.getMajorVersion(); // 1
int minor = v.getMinorVersion(); // 2
int patch = v.getPatchVersion(); // 3

Label preRelease = v.getPreReleaseLabel();
String stage = preRelease.getPart(0); // "alpha"
String stageAttempt = preRelease.getPart(1); // "12"

Label build = v.getBuildLabel();
String buildId = build.getPart(1); // "043"
```

### Building

``` java
Version version = Version.builder(1, 2, 3)
		.preReleaseLabel("alpha", "12")
		.buildLabel("build", "043")
		.build();

String versionString = version.toString(); // "1.2.3-alpha.12+build.043"
```

### Comparing and Ordering

``` java
Version latest = Stream.of("2.0.0", "1.2.3", "1.2.4", "2.0.0-alpha", "1.2.2", "2.0.0-alpha")
		.map(Version::from)
		.sorted(new VersionComparator()::compare)
		.findFirst()
		.orElse(null);
```

## Version Range Selectors
Expressions can be used to describe a range of versions to be selected. These 
selectors mimic the NPM style version selectors.

### Examples
Simple version ranges are made up of a version and an operator to describe the
range being created.
* `1.2.0` includes only version 1.2.0
* `^1.2.0` includes all versions greater than or equal to 1.2.0 with the same major version.
* `~1.2.0` includes all versions greater than or equal to 1.2.0 with the same major and minor version.
* `<1.2.0` includes all versions less than 1.2.0
* `<=1.2.0` includes all versions less than or equal to 1.2.0
* `>1.2.0` includes all versions greater than 1.2.0
* `>=1.2.0` includes all versions greater than or equal to 1.2.0

More complex version ranges are created by joining simple ranges.
* `^1.2.0 <1.4.0` includes all versions satisfying both `^1.2.0` AND `<1.4.0`
* `^1.2.0 || <0.1.0` includes all versions satisfying either `^1.2.0` OR `<0.1.0`
* `^1.2.0 <1.4.0 || <0.1.0` Note here that AND take precedence over OR.
* `^1.2.0 (<1.4.0 || <0.1.0)` Precedence can be changed with parenthesis.

### Grammar
```
<selector>    := <intersection>
                 <intersection> || <selector>

<intersection := <group>
                 <group> <intersection>

<group>       := <range>
                 ( <selector> )

<range>       := <range op><version>
                 <version>

<range op>    := "^", "~", "<", "<=", ">", ">=", <empty>

<version>     := See semver.org
```

### Parsing
```java
VersionRange range = VersionRange.from(">1.2.3-alpha.2 <2.0.0");

List<Version> matched = allVersions
		.stream()
		.filter(range::contains)
		.collect(Collectors.toList());
```

### Building
Expressions can be programatically built if needed. To do this, utilize the 
`SimpleRange` class to build a range from a version and for more complicated
scenarios you can use `JoinedRange` to create a union or intersection of
of other ranges.

```java
VersionRange range1 = new SimpleRange(RangeOperator.CARAT, Version.from("1.2.3"));
VersionRange range2 = new SimpleRange(RangeOperator.LESS_THAN, Version.from("2.3.4"));
VersionRange joinedRange = new JoinedRange(range1, JoinOperator.INTERSECTION, range2);

boolean valid = joinedRange.contains(version);
```