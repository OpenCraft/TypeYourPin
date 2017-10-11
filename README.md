# TypeYourPin
TypeYourPin is an android component made to provide a better experience when user types some PIN code

![Dismissable Library Animation](https://media.giphy.com/media/3ov9jEDs6E9YL4XAuA/giphy.gif)

## Installation:
1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency
```
dependencies {
  compile 'com.github.OpenCraft:TypeYourPin:-SNAPSHOT'
}
```

## Usage:
Start using the TypeYourPin component by adding it inside your layout file:
```
<com.opencraft.gcherubini.typeyourpin.TypeYourPinLayout
        android:id="@+id/type_your_pin_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```


If you would like to know what was the pin typed, just pass the interface 'TypeYourPinInterface' to your TypeYourPin component items as below:
```
  yourTypePinLayout.setTypeYourPinInterface(new TypeYourPinInterface() {
      @Override
      public void onPinTyped(String typedPin) { }
  });
```

## Customizing: 
If you want to customize the pin's views, override the default drawable files with your custom ones: 
```
type_your_pin_rounded_shape_filled.xml
type_your_pin_rounded_shape_transparent.xml
```

Create your own res/values/integers.xml to change the PIN lenght and the soft keyboard input type:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
  <integer name="type_your_pin_lenght">4</integer>
  <integer name="type_your_pin_input_type">2</integer> <!-- TYPE_CLASS_NUMBER -->
</resources>
```
Create your own res/values/dimens.xml to change the pin size and its margins:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="type_your_pin_size">40dp</dimen>
    <dimen name="type_your_pin_margins">30dp</dimen>
</resources>
```
If you would like to get soft keyboard opened when your activity is shown, change your manifest file with:
```
<activity 
  android:name=".MainActivity" 
  android:windowSoftInputMode="stateAlwaysVisible" />
```
