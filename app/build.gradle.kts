***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***

***REMOVED***
    compileSdk = 32

***REMOVED***
        applicationId = "com.xtapps.messageowl"
***REMOVED***
        targetSdk = 32
***REMOVED***
***REMOVED***

***REMOVED***

***REMOVED***
    ***REMOVED***

***REMOVED***

***REMOVED***
    ***REMOVED***
***REMOVED***
***REMOVED***
            isMinifyEnabled = false
***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***
        ***REMOVED***
    ***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***
    ***REMOVED***
***REMOVED***
***REMOVED***
    ***REMOVED***
***REMOVED***

***REMOVED***

    val roomVersion = "2.4.2"

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.7.0-alpha02")
***REMOVED***
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
***REMOVED***
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***
***REMOVED***

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))

    // Declare the dependency for the Firebase Authentication library
    // When using the BoM, you don"t specify versions in Firebase library dependencies
***REMOVED***
***REMOVED***