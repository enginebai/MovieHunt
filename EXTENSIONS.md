# Useful Extension Functions
## View
```kotlin
// Prevent multiple/duplicate click
View.debounceClick() { ... } 

// Visibility
View.visible()
View.invisible()
View.gone()
View.showIf { ... }
View.hideIf { ... }
View.goneIf { ... }

// Keyboard
View.showKeyboard()
View.hideKeyboard()

// Dimension
View.px2dp()
View.dp2px()
View.px2sp()
View.sp2px()

// Resources
View.getColor(R.color.colorPrimary)
View.getDrawable(R.drawable.ic_launcher)
```

## EditText
```kotlin
EditText.textChanged { s -> ... }
EditText.textChanged().doOnNext { s -> ... }.subscribe()
EditText.validate(errorMessage = "The name should not be empty") { !it.isNullOrBlank() }
EditText.validateEmail { ... }
EditText.validateEmail("It's not valid email")
EditText.showPassword()
EditText.hidePassword()
```

## TextView
```kotlin
// Set view gone while text is null or empty.
TextView.setTextWithExistence("1234")

// Set view invislble while text is null or empty.
TextView.setTextWithVisibility("5678")
```

## String
```kotlin
String?.isValidEmail(): Boolean
```

