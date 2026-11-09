# Garbage Management App

An Android app for managing and coordinating everything around garbage collection: clients, staff, collection bins, complaints, feedback and payments.

## Features

- **Clients** sign up, pay for collection, file complaints and send feedback.
- **Staff** are registered and managed by an administrator.
- **Admin** views every client, staff member, complaint and feedback entry.
- Location capture for clients and pickups.
- Mobile payments through Flutterwave (Rave).

## Stack

- Android (Java), AndroidX, Navigation component, ViewModel and LiveData
- Firebase Realtime Database
- Google Play Services Location
- Flutterwave Rave Android SDK

## Running

1. Open the project in Android Studio.
2. Replace `app/google-services.json` with the config from your own Firebase project.
3. Put your Flutterwave test keys in `RaveConstants.java`.
4. Run on a device or emulator (minSdk 21).
