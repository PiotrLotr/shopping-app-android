package com.example.shoppingapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

//    private var galleryManager = Singleton()
//

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent) {
            super.onReceive(context, intent)
            if(intent?.action == "com.example.testwidget.action1"){
                val appWidgetId = intent.getIntExtra("appWidgetId", 0)

                Log.d("DEBUG_LOG", "CHANGING BACKGROUND IMAGE...")
                Log.d("DEBUG_LOG","${Singleton.getPos()}")

                val views = RemoteViews(context?.packageName, R.layout.widget_layout)

                if (Singleton.getPos() == 0) {
                    views.setImageViewResource(R.id.backgroundImage, R.drawable.healthy_groceries)
                    Singleton.switchPos(1)
                } else {
                    views.setImageViewResource(R.id.backgroundImage, R.drawable.pear)
                    Singleton.switchPos(0)
                }
                Log.d("DEBUG_LOG","${Singleton.getPos()}")

                if(context!= null){
                    AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
                }
            }
        }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
//    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.widget_layout)


    // redirect to website:
    val wwwIntent = Intent(Intent.ACTION_VIEW)
    wwwIntent.data = Uri.parse("https://www.glovo.pl")
    val pendingwwwIntent = PendingIntent.getActivity(
        context,
        1,
        wwwIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.showWebPageBT, pendingwwwIntent)

    // change widget background image:
    val loopbackIntent = Intent("com.example.testwidget.action1")
    loopbackIntent.putExtra("appWidgetId", appWidgetId)
    loopbackIntent.component = ComponentName(context, AppWidget::class.java)
    val pendingLoopbackIntent = PendingIntent.getBroadcast(
        context,
        1,
        loopbackIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.changeImageBT, pendingLoopbackIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

object Singleton{

    var actualPos: Int
    var gallery = ArrayList<Int>()

    init {
        actualPos = 0
//        gallery.add(R.drawable.pear)            // 0
//        gallery.add(R.drawable.shopping_app_1)
    }

    fun getPos(): Int{
        return actualPos
    }

    fun switchPos(value: Int){
        this.actualPos= value
    }

//    fun getNext(): Int{
//        if(actualPos == gallery.size){
//            this.actualPos = 0
//        } else {
//            this.actualPos++
//        }
//        return gallery[actualPos]
//    }

    fun addInfoToLog(){
        Log.d("DEBUG_LOG", "POS: $actualPos")
    }
}




