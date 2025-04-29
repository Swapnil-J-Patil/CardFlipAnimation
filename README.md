# 💳 Jetpack Compose Credit Card Flip Stack

A fully customizable, animated stack of credit cards built using Jetpack Compose. Easily flip through cards with dynamic properties like cardholder name, card number, CVV, expiry date, and brand logo. Smooth animations give your app a polished, interactive feel!


<img src="https://github.com/user-attachments/assets/dfffc1ea-64b5-448a-bb29-1084d6afc233" alt="Card Animation" width="250">

---

## ✨ Features

- 💥 Beautiful flip & stack animations
- 🎨 Fully customizable card content
- 🔒 Secure display with optional CVV
- 🧩 Support for brand logos or custom brand text
- 📱 Dynamic layout with responsive UI
- 🧰 Built with **Jetpack Compose**

---

## 🚀 Installation

Add the following lines in your `settings.gradle.kts`:

```settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

```
Add the following dependency in your `build.gradle.kts` (Module: app):

```gradle
dependencies {
	implementation ("com.github.Swapnil-J-Patil:CardFlipAnimation:v1.0.0"
}
```

## 🎨 Customization

| Parameter                   | Type       | Description                                                                 |
|----------------------------|------------|-----------------------------------------------------------------------------|
| `modifier`                 | `Modifier` | Compose modifier for layout customization                                  |
| `baseColor`                | `Color`    | Base color used for the card's background                                  |
| `cardNumber`               | `String`   | Credit card number to display                                              |
| `cardHolder`               | `String`   | Name of the cardholder                                                     |
| `expires`                  | `String`   | Expiry date in "MM/YY" format                                              |
| `cvv`                      | `String`   | 3-digit CVV code shown on the card                                         |
| `brand`                    | `String`   | Brand name to display if `isText` is true (e.g. "VISA", "MasterCard")     |
| `priority`                 | `Float`    | Determines card size/scale in stack (e.g. 1f for front card)               |
| `index`                    | `Int`      | Position of the card in the stack (affects Y-offset)                       |
| `color`                    | `Color`    | Foreground overlay color for the card                                      |
| `onMoveToBack`             | `() -> Unit` | Callback invoked when the card flips to the back                           |
| `image`                    | `Int`      | Resource ID of the brand image (e.g. `R.drawable.visa`)                    |
| `imageHeight`              | `Dp`       | Height of the brand image (default: `70.dp`)                               |
| `imageWidth`               | `Dp`       | Width of the brand image (default: `80.dp`)                                |
| `imageYOffset`            | `Dp`       | Vertical offset for brand image (default: `22.dp`)                         |
| `imageXOffset`            | `Dp`       | Horizontal offset for brand image (default: `10.dp`)                       |
| `isText`                   | `Boolean`  | If true, brand name is shown as text instead of image                      |

## 📖 Usage

To use the `Card Flip Animation`, follow this example:

```kotlin
val priorities = remember { mutableStateListOf(1f, 0.95f, 0.9f, 0.85f) } \\Add more values like 0.80,0.75 to add more cards in the stack
val colors = remember {
    mutableStateListOf(
        Color(0xFFFF9800), // Bright Yellow-Orange
        Color(0xFF06740B), // Bold Green
        Color(0xFF921942), // Rich Pink
        Color(0xFF035FAF), // Vibrant Blue
    )
}
val cardNumber = remember {
    mutableStateListOf(
        "1234567890123456",
        "1234567890126653",
        "1234567890122257",
        "1234567890121145",
    )
}
val cardHolder = remember {
    mutableStateListOf(
        "Swapnil Patil",
        "Vaibhav Mane",
        "Sanket Mhatre",
        "Ganesh Shinde",
    )
}
val cardExpiry = remember {
    mutableStateListOf("01/29", "05/27", "03/28", "02/26")
}
val cvv = remember {
    mutableStateListOf("445", "365", "895", "624")
}
val images = remember {
    mutableStateListOf(
        R.drawable.visa,
        R.drawable.mastercard,
        R.drawable.visa,
        R.drawable.mastercard
    )
}

Box(
    Modifier
        .fillMaxSize()
        .padding(8.dp)
        .background(Color.White),
    contentAlignment = Alignment.BottomCenter
) {
    for (i in 3 downTo 0) {
        BankCardUi(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            baseColor = colors[i],
            cardNumber = cardNumber[i],
            cardHolder = cardHolder[i],
            expires = cardExpiry[i],
            cvv = cvv[i],
            brand = "VISA",
            color = colors[i],
            priority = priorities[i],
            index = i,
            onMoveToBack = {
                // Rotate card stack
                priorities.add(priorities.removeAt(0))
                colors.add(colors.removeAt(0))
                cardNumber.add(cardNumber.removeAt(0))
                cardHolder.add(cardHolder.removeAt(0))
                cardExpiry.add(cardExpiry.removeAt(0))
                cvv.add(cvv.removeAt(0))
                images.add(images.removeAt(0))
            },
            image = images[i]
        )
    }
}


```

## 🛠️ License

This project is licensed under the MIT License.

## 🌟 Show Your Support

If you find this repository helpful, don’t forget to ⭐ star the repo!


