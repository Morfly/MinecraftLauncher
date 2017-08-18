# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# set(prebuilds_DIR prebuilts)

# set(ANDROID_STL_TYPE gnustl_shared)

set(CMAKE_CXX_STANDARD_LIBRARIES ${CMAKE_SOURCE_DIR}/prebuilts/libgnustl_shared.so)

add_library(genericlauncher_tinysubstrate SHARED IMPORTED)
set_target_properties(genericlauncher_tinysubstrate PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/prebuilts/genericlauncher_tinysubstrate.so)

# add_library(libgnustl_shared SHARED IMPORTED)
# set_target_properties(libgnustl_shared PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/prebuilts/libgnustl_shared.so)


# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             genericlauncher
             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/dobby.cpp
             src/main/cpp/main.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       genericlauncher
                       android
                       genericlauncher_tinysubstrate
                       # libgnustl_shared
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )