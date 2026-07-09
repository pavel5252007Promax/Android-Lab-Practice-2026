package com.example.pomodorotimer
import android.content.Context
import android.media.MediaPlayer

class SoundManager (private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playSound(soundResId: Int) {
        try {
            stopSound()

            mediaPlayer = MediaPlayer.create(context, soundResId)
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopSound() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }

    fun release() {
        stopSound()
    }
}