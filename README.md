# SkinSease

## Introduction
Indonesian constitution clearly stated health as an important objective, and achieving the highest possible level of health for all remains a major priority of national development plans and international commitments. However, due to several complications, lots of people remain at a disadvantage when it comes to health. In today’s modern world, paying attention to personal health is a must. Regardless of where they live and the budget they have. Sometimes, people in certain places have a hard time finding a hospital, or even doctors. Sometimes when people spot abnormalities on their skin, they ignore it because they think it's normal. But it can be a symptom of a severe disease that has to be treated seriously.

We made a Machine Learning model to detect skin conditions. We divided the model into different parts which are Dermatofibroma, Melanoma, Pigmented Benign Keratosis, Seborrheic Keratosis, Squamous Cell Carcinoma, and Vascular Lesion. This application uses Google Firebase to authenticate & register users, and store needed data. We use the Android app as the main platform to implement all the features.

Our app can help people to do simple diagnosis on their skin condition. The app also can give suggestions about the medicine or ointment they should take. That way, they can relax when they realize it's not something dangerous, and if it is, they can get treatment and buy medicine as the app suggests faster.

- Machine Learning: exploration data analysis using matplotlib, image augmentation using Keras Preprocessing and ImageDataGenerator, building and fitting models with pre-trained models from TensorFlow lite model maker.
- Android: deployment of TensorFlow lite for skin diseases’ image classification and a real-time connection using Firebase (Firestore Database) for the authentication of users.
- Cloud: Setting up and implementing firebase and its products with the billing enabled. The products that are used are Firestore Database, Realtime Database, and Storage.

## Features

## Dataset Link:
- https://www.kaggle.com/mdsafayet/multiclass-disease-classification (original)
- https://www.kaggle.com/aurellashilla/skin-test (filtered to 6 skin disease classes) 
