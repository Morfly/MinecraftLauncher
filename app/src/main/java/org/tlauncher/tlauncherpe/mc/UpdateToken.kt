package org.tlauncher.tlauncherpe.mc

import com.firebase.client.Firebase

fun UpdateToken(userId: String, token: String) {
    val ref = Firebase("https://tlauncher-cb626.firebaseio.com/")
    ref.child("Users").child(userId).setValue("id: $token")
}