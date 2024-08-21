# ContentProviderSample

This project demonstrates the use of a `ContentProvider` in Android to securely share data between two applications. The project includes two apps: **App A** (which defines the `ContentProvider`) and **App B** (which accesses the data provided by App A).

## Project Structure

- **App A** (`com.example.appa`): 
  - This app defines a `ContentProvider` that exposes a simple string value. The data is protected with a custom permission to ensure that only authorized apps can access it.
  - Includes the definition of the custom permission in the manifest.

- **App B** (`com.example.appb`): 
  - This app consumes the data provided by App A's `ContentProvider`. It requests the custom permission in the manifest and uses a `ContentResolver` to query the data.

## Features

- **Custom Permissions**: Protects the `ContentProvider` in App A using a custom permission (`com.example.appa.READ_PLAYERID`).
- **Cross-App Data Sharing**: Demonstrates how to securely share data between two different apps.
- **Android 11+ Compatibility**: Handles the new restrictions on `ContentProvider` access introduced in Android 11.

## Setup Instructions

### 1. Setting Up App A (Provider)

1. **Define the ContentProvider:**
   - The `ContentProvider` is defined in App A's `AndroidManifest.xml` with a custom permission.

   ```xml
   <provider
       android:name=".MyContentProvider"
       android:authorities="com.example.appa.provider"
       android:exported="true"
       android:readPermission="com.example.appa.READ_PLAYERID" />

2. **Custom Permission:**
   - A custom permission `com.example.appa.READ_PLAYERID` is defined to restrict access to the `ContentProvider`.

   ```xml
   <permission
       android:name="com.example.appa.READ_PLAYERID"/>
   ```

### 2. Setting Up App B (Consumer)

1. **Request Permission:**
   - App B must request the custom permission defined in App A to access the `ContentProvider`.

   ```xml
   <uses-permission android:name="com.example.appa.READ_PLAYERID" />
   ```

2. **Query ContentProvider:**
   - App B accesses the data using the `ContentResolver`:

   ```kotlin
   val uri = Uri.parse("content://com.example.appa.provider/name")
   val cursor = contentResolver.query(uri, null, null, null, null)

   cursor?.let {
       if (it.moveToFirst()) {
           val name = it.getString(it.getColumnIndexOrThrow("name"))
           textView.text = "Name from App A: $name"
       }
       cursor.close()
   } ?: Log.e("AppB", "Cursor is null")
   ```

3. **Queries Declaration (for Android 11+):**
   - Starting with Android 11, declare the intent to query App A in the manifest:

   ```xml
   <queries>
       <package android:name="com.utility.appa" />
   </queries>
   ```

## Testing the ContentProvider Using ADB

You can use ADB commands to test the `ContentProvider` functionality. However, note that with custom permissions, ADB access might be restricted depending on the configuration.

### ADB Commands

1. **Query ContentProvider from ADB:**
   - You can use the following ADB command to query the `ContentProvider` directly from the terminal:

   ```bash
   adb shell content query --uri content://com.example.appa.provider/name
   ```

   **Expected Output:**
   If the command is successful, it will return:

   ```bash
   Row: 0 name=John Doe
   ```

   **If You See a SecurityException:**
   If you receive a `SecurityException`, it indicates that the ADB shell doesn't have the necessary permissions to access the `ContentProvider`.

2. **Test Permissions and Access in App B:**
   - Ensure that App B can access the `ContentProvider`:

   - Use `Logcat` in Android Studio to monitor the output when App B attempts to access the `ContentProvider`.

   ```kotlin
   Log.e("AppB", "SecurityException: ${e.message}")
   ```

## Known Issues and Troubleshooting

- **SecurityException:** If App B cannot access the `ContentProvider`, ensure that both apps are signed with the same key and that permissions are correctly declared.
- **Provider Not Found:** If App B fails to find the `ContentProvider`, ensure that App A is installed and has been run at least once on the device.

## Conclusion

This project demonstrates secure data sharing between two Android apps using a `ContentProvider`. By following the setup and testing instructions, you can ensure that the `ContentProvider` is properly configured and accessible by authorized apps.
