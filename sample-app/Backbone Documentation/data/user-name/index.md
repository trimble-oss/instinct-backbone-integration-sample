# UserName

[androidJvm]\
data class UserName(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), firstName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lastName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), middleName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) : Publisher.Update

Maps the instinct user's id to the user's human names Is also a Publisher.Update to update the user Instinct ID to human name mapping.

## Constructors

| | |
|---|---|
| UserName | [androidJvm]<br>fun UserName(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), firstName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lastName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), middleName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |

## Types

| Name | Summary |
|---|---|
| Companion | [androidJvm]<br>object Companion : Backbone.Retriever&lt;[Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), UserName&gt;&gt; <br>Backbone.Retriever to get the human name for all users. A Map of Instinct user ID to UserName is retrieved. |

## Properties

| Name | Summary |
|---|---|
| firstName | [androidJvm]<br>val firstName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the Backbone json data |
| lastName | [androidJvm]<br>val lastName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| middleName | [androidJvm]<br>~~val~~ ~~middleName~~~~: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Deprecated - optional middle name |
| title | [androidJvm]<br>~~val~~ ~~title~~~~: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Deprecated - optional user title |
| userId | [androidJvm]<br>val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the user in Backbone, eFleetSuite, and other instinct apps |
| values | [androidJvm]<br>open override val values: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;&gt;<br>are key value pairs that should be added to the json. If the value is null then the field will be removed from the json instead of added. |