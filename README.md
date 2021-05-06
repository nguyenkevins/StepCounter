![alt text](https://github.com/nguyenkevins/StepCounter-AndroidDemo/blob/main/ProjectImageStepCounter.png)

# Step Counter - Android Demo

A demo of utilizing the device's motion sensor, specifically using the step counter sensor.

## Description

This mobile programming sample was made to continue with the software engineering internship application at Tidyware, located in Bellevue (WA). The sample requirement for creating an android application of the project includes using Kotlin and motion sensors to create an app with functionality. I have provided a small write-up about the project below. 

## Getting Started 

### Dependencies

* Android SDK
* Android API Level 27
* com.mikhaellopez:circularprogressbar:3.0.3 (Circular Progress Bar/Included in source)
* Kotlin Android Extensions (Included in source)

### Installing

* First, install Android Studio and Android SDK (get latest version).
* You can use HTTPS, SSH, or GitHub CLI to install the source (provided from the repository).
* Next, import the source using gradle on Android Studio.

### Executing program

* First, find an Android device you want to use the app on with Android 8.1 (API Level 27).  
* Second, with the Android device, use a charger to connect the Android device to a computer and on Android go to Setting > About Phone > Tap on "Build number" 4 times > Go back to go to System > Developer Options > Enable USB Debugging (Not all android device follow the exact same pattern, but it should be close to this structure).
* Third, assuming the project source is installed using gradle on Android Studio, you can click on the green play button to run the app. The app should be compiled and will install the app on the phone.
* Fourth, go to your Android device and run the application (Remember to allow physical activity permission).

## Feature

```
* Step Sensor, which detects step movement from users
* Save/Load data in order to keep steps after closing/pausing app
* Circular Progress Bar
* Dark/Light Mode compatible with simple design
* Reset Counter
* Modify maximum steps
```

## Screenshot
![alt text](https://github.com/nguyenkevins/StepCounter-AndroidDemo/blob/main/StepCounterScreenshots.png)

## Authors

Kevin Nguyen - https://www.linkedin.com/in/nguyenkevins/


## Version History

* 1.0
    * Initial Release
    * There are a few bugs. I will look at them if necessary.

## License

This project is licensed under the [GNU General Public License v3.0] License - see the LICENSE.md file for details

## Acknowledgments

```
* Android Developer Motion Sensor - https://developer.android.com/guide/topics/sensors/sensors_motion
* Code Palace                     - https://www.youtube.com/watch?v=WSx2a99kPY4
* Circular Progress Bar           - https://github.com/lopspower/CircularProgressBar
```
