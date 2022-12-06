package abolfazli.mahdi.ginitask.main

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    private val TAG = "MainViewModel"
    fun log() {
        Log.e(TAG, "log: ----------------" )
    }
}