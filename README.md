[![Build](https://github.com/saynotobugsorg/confidence/actions/workflows/main.yml/badge.svg?label=main)](https://github.com/saynotobugsorg/confidence/actions/workflows/main.yml)
[![codecov](https://codecov.io/gh/saynotobugsorg/confidence/branch/main/graph/badge.svg?token=3wGxOPmEEc)](https://codecov.io/gh/saynotobugsorg/confidence)

# Confidence

A declarative Java Assertion Framework.

Confidence makes it easier to write Java Unit tests that give you great confidence in your code with little effort.

# Note

Confidence is still under development. All parts should be considered subject to change.

# Declarative Testing

Declarative testing means focusing on the **What** instead of the **How**.

Any unit under test (typically a class) has two aspects:
* **What** it is meant to do and
* **How** you have to use it.

The **How** is, to a large extend, determined by the interface of a class or the signature of a function. In case of mutable classes and non-pure functions the order interactions may also be relevant. In any case though, the **How** is typically very static and, to some extent, also enforced by the compiler. That means we often can use the same methods for testing various implementations of the same type, we just need to provide different data and assert different behavior. That's the **What**. A declarative test leaves the **How** to the test framework and only describes the **What**.

## Example

The classic non-declarative test of a `Predicate` might look like this:

```java
assertTrue(new IsEven().test(2));
assertFalse(new IsEven().test(3));
```

It contains interface details like the fact that you call the `test` method and that it returns `true` in case the argument satisfies the `Predicate`.

The declarative test might look like this

```java
assertThat(new IsEven(),
    is(allOf(
        satsifiedBy(2),
        not(satisfiedBy(3))
    )));
```

In this case we don't see **how** the instance is tested, we just describe **what** we expect, namely that `2` satisfies the `Predicate` and `3` doesn't.
All the method calls and result evaluation are performed by the `satisfiedBy` `Quality`, which can be used for every `Predicate` implementation

## Qualities

In Confidence, you use [`Quality`](https://github.com/saynotobugsorg/confidence/blob/main/confidence-core/src/main/java/org/saynotobugs/confidence/Quality.java)s to express **what** you expect of the unit under test. As seen above, `Quality`s are composable to express even complex behavior.
Confidence already provides many `Quality` implementations, but to use its full power you should
write custom `Quality`s for your own types.

# Writing custom `Quality` implementations

Confidence already comes with a number of useful `Quality`s that cover many JDK types.
Yet, it is important to be able to write custom implementations. Ideally you provide
a library with `Qualitiy`s for all types you declare in your own code. That makes it easier for you and others (for instance users of your library) to write tests.

## Composing Qualities

In many cases you can write a new `Quality` by composing it from already existing ones.
In fact, many of the `Quality`s in the `confidence-core` module are just compositions of 
simpler `Quality`s.

### Example

This is the implementation of the [`EmptyCharSequence`](https://github.com/saynotobugsorg/confidence/blob/main/confidence-core/src/main/java/org/saynotobugs/confidence/quality/charsequence/EmptyCharSequence.java) `Quality`, that describes `CharSequences`
and `String` with a length of `0`.

```java
@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class EmptyCharSequence extends QualityComposition<CharSequence>
{
    public EmptyCharSequence()
    {
        super(new Satisfies<>(c -> c.length() == 0, new Text("<empty>")));
    }
}

```

This creates a new `Quality` composition based on an existing `Satisfies` `Quality`.
`Satisfies` takes a `Predicate` that must be satisfied for the `Quality` to be satisfied and a `Description` of the expectation. By default, the fail `Description`
is the actual value, but `Satisfies` takes an optional argument to create a more adequate fail `Description` for a given actual value.

The annotation

```
@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
```
ensures a static factory methods like the following is automatically created in a class called `Core`:

```java
public static EmptyCharSequence emptyCharSequence() {
    return new org.saynotobugs.confidence.quality.charsequence.EmptyCharSequence();
}
```

## Testing Qualities

Classic non-declarative tests often times have a major flaw: the (often times very imperative) test code is not tested itself. After all, you only can trust your production code, when you can trust the test code too.

The functional ideas Confidence is built upon, makes it easy to test
`Quality`s and ensure the **how** has full test coverage.

Confidence makes it easy to test a `Quality`. Just describe the expected behavior when you provide instances that are expected to pass and some that are expected to fail the assertion of the `Quality` under test:

```java
assertThat(new EmptyCharSequence(),    // The Quality under test.
    new AllOf<>(
        new Passes<>(""),              // An example that should pass the test.
        new Fails<>(" ", "\" \""),     // Examples that should fail the test …
        new Fails<>("123", "\"123\""), // … along with the resulting description.
        new HasDescription("<empty>")  // The description of the Quality.
    ));
}
```



# Switching from Hamcrest

As a Hamcrest user you'll find it easy to switch to Confidence. The core idea is the same: Composable components to describe he expected behavior of your code. In Hamcrest these are called `Matcher` in Confidence they are called `Quality`.

There are some significant differences though:

* In case of a mismatch, Hamcrest (for Java) needs to run the `Matcher` again to get a mismatch description, a Confidence `Quality` returns an [`Assessment`](https://github.com/saynotobugsorg/confidence/blob/main/confidence-core/src/main/java/org/saynotobugs/confidence/Assessment.java) that contains the result and a description of the issue (in case the assessment failed).
* Confidence makes it easier to produce comprehensible descriptions, closer to what Assertj or Google Truth produce, by using composable [Descriptions](https://github.com/saynotobugsorg/confidence/blob/main/confidence-core/src/main/java/org/saynotobugs/confidence/Description.java)
* In Confidence the `Contains` `Quality` has the same semantics as Java `Collection.contains(Object)`
* Confidence has out ouf the box support for testing `Quality` implementations.

There are also some noticeable differences in how some of the core `Quality` implementations are being
called or used. The following table shows the most important ones.


General note on matching arrays: arrays (including ones of primitive types) can be matched with matchers to match `Iterable`s decorated with `arrayThat(…)`.

| Hamcrest | Confidence                            |
|---|---------------------------------------|
| `contains(...)` | `iterates(...)`                       |
| `containsInAnyOrder(...)` | `iteratesInAnyOrder(...)`             |
| `iterableWithSize(...)` | `hasNumberOfElements(...)`            |
| `hasItem(...)` | `contains(...)`                       |
| `hasItems(...)` | `contains(...)`                       |
| `everyItem(...)` | `each(...)`                           |
| `sameInstance(...)`, `theInstance(...)` | `sameAs(...)`                         |
| `matchesRegex(...)`, `matchesPattern(...)` | `matchesPattern(...)`                 |
| `array(...)` | `arrayThat(iterates(...))`*           |
| `hasItemInArray(...)` | `arrayThat(contains(...))`*           | 
| `arrayWithSize(...)` | `arrayThat(hasNumberOfElements(...))`* |

*works with arrays of primitive types

Confidence provides an adapter to use Hamcrest `Matcher`s in Confidence assertions.
The adapter `Quality` is called `hamcrest` and you just pass a `Matcher` to it like in:

```java
assertThat(List.of(1,2,5,10,11), hamcrest(hasItem(2)));

```

# JUnit Confidence TestEngine

One of the goals of Confidence is to eliminate any imperative code from unit tests. Unfortunately, with Jupiter you still need to write at least one very imperative `assertThat` statement.

That's why the `confidence-incubator` module contains an experimental JUnit TestEngine to remove this limitation.

With the ConfidenceEngine you no longer write statements. Instead, you declare `Verifiable`s that are verified when the test runs.

Check out the [`HasPatchTest`](https://github.com/dmfs/semver/blob/main/semver-confidence/src/test/java/org/dmfs/semver/confidence/HasPatchTest.java) from the [dmfs/semver](https://github.com/dmfs/semver/tree/main) project. It verifies that the `HasPatch` `Quality` is satisfied by certain `Version`s.

```java
@Confidence
class HasPatchTest
{
    Verifiable has_patch_int = assertThat(
        new HasPatch(5),
        allOf(
            passes(mock(Version.class, with(Version::patch, returning(5)))),
            fails(mock(Version.class, with(Version::patch, returning(4))), "had patch <4>"),
            hasDescription("has patch <5>")
        )
    );

    Verifiable has_patch_quality = assertThat(
        new HasPatch(greaterThan(4)),
        allOf(
            passes(mock(Version.class, with(Version::patch, returning(5)))),
            fails(mock(Version.class, with(Version::patch, returning(4))), "had patch <4>"),
            hasDescription("has patch greater than <4>")
        )
    );
}
```

The class is annotated with `@Confidence` to make it discoverable by the `ConfidenceEngine`. 

There are no statements in that test, not even test methods.
The test only declares certain `Verifiable`s that are verified by the test engine. It should be noted that the `assertThat` method is not the same as in the other tests above.

Also, there are no `Before` or `After` hooks. The idea is to make those part of the `Verifiable` using composition. For instance, when a test requires certain resources you'd apply the `withResources` decorator like in the following test, that requires a git repository in a temporary directory:

```java
    Verifiable default_strategy_on_clean_repo = withResources(
        new TempDir(),
        new Repository(
            getClass().getClassLoader().getResource("0.1.0-alpha.bundle"),
           "main"),

        (tempDir, repo) -> assertThat(
            new GitVersion(TEST_STRATEGY, new Suffixes(), ignored -> "alpha"),
            maps(repo, to(preRelease(0, 1, 0, "alpha.20220116T191427Z-SNAPSHOT")))));
```

The `withResources` decorator creates the required resources before the
assertion is made and cleans up afterward.

The Confidence Engine is still in an early ideation phase. You're welcome to try it and make suggestions or contributions for improvements.