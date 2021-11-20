package com.example.shoppingapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.shoppingapp.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding
    var fontSize = 14f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appSettings: SharedPreferences = getSharedPreferences("AppSettingsPreferences", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = appSettings.edit()
        val isNightModeOn: Boolean = appSettings.getBoolean("NightMode", false)

        // previous state
        if(isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // switch button listener
        binding.darkModeBT.setOnClickListener(){
            if(isNightModeOn)
                switchMode(1, false, editor)
            else
                switchMode(2, true, editor)
            }

        binding.returnToMenuBT.setOnClickListener(){
            val intentReturnToMenu = Intent(this, MainActivity::class.java)
            startActivity(intentReturnToMenu)
        }

        // previous state
        fontSize = appSettings.getFloat("FontSize", 14f)
        changeFontSize(editor)

        // switch button listener
        binding.fontSizeUpIB.setOnClickListener(){
            fontSize += 4f
            changeFontSize(editor)
        }
        binding.fontSizeDownIB.setOnClickListener(){
            fontSize -= 4f
            changeFontSize(editor)
        }
    }

    //
    private fun switchMode (mode: Int, isSwitched: Boolean, editor: SharedPreferences.Editor){
        AppCompatDelegate.setDefaultNightMode(mode)
        editor.putBoolean("NightMode", isSwitched)
        editor.apply()
    }

    private fun changeFontSize(editor: SharedPreferences.Editor) {
        // elements to change:
        binding.darkModeBT.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        binding.appSettingsHeaderTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        binding.fontTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)

        editor.putFloat("FontSize", fontSize)
        editor.apply()
    }


}




