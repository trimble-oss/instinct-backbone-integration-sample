# CurrentUser

[androidJvm]\
data class CurrentUser(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : Publisher.Input

Publisher.Input identifying the userId of the user who is the current user of the tablet.

## Constructors

| | |
|---|---|
| CurrentUser | [androidJvm]<br>fun CurrentUser(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Types

| Name | Summary |
|---|---|
| Companion | [androidJvm]<br>object Companion : Backbone.Retriever&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; <br>Backbone.Retriever for CurrentUser |

## Functions

| Name | Summary |
|---|---|
| asJSON | [androidJvm]<br>open override fun asJSON(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Formats data as JSON to store in Backbone |

## Properties

| Name | Summary |
|---|---|
| key | [androidJvm]<br>open override val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>identifies the Backbone data |
| userId | [androidJvm]<br>val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>of the user identified as the driver |