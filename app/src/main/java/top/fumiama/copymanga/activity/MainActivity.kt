package top.fumiama.copymanga.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_main.*
import top.fumiama.copymanga.R
import top.fumiama.copymanga.handler.MainHandler
import top.fumiama.copymanga.view.JSWebView
import top.fumiama.copymanga.web.JS
import top.fumiama.copymanga.web.JSHidden
import top.fumiama.copymanga.web.WebChromeClient
import java.lang.ref.WeakReference

class MainActivity: Activity() {
    var wh: JSWebView? = null
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wm = WeakReference(this)
        mh = Looper.myLooper()?.let { MainHandler(it) }

        WebView.setWebContentsDebuggingEnabled(true)
        w.setWebViewClient("i.js")
        w.webChromeClient = WebChromeClient()
        w.loadJSInterface(JS())
        w.loadUrl(getString(R.string.web_home))

        wh = JSWebView(this, getString(R.string.pc_ua))
        wh?.setWebViewClient("h.js")
        wh?.loadJSInterface(JSHidden())
    }

    override fun onBackPressed() {
        if(w.canGoBack()) w.goBack()
        else super.onBackPressed()
    }

    fun onFabClicked(v: View){
        startActivity(Intent(this, DlActivity::class.java))
    }

    companion object{
        var wm: WeakReference<MainActivity>? = null
        var mh: MainHandler? = null
    }
}