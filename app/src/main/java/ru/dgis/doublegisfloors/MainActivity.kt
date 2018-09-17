package ru.dgis.doublegisfloors

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import ru.dgis.doublegisfloors.model.FirmDetailedViewImpl
import ru.dgis.doublegisfloors.model.FirmDto
import ru.dgis.doublegisfloors.model.FirmListDto

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: RvAdapter
    private lateinit var webView: WebView
    private lateinit var recyclerView: RecyclerView

    private var selectedFirm: FirmDto? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.am_toolbar)
        setSupportActionBar(toolbar)

        val inflater = LayoutInflater.from(this)

        recyclerView = findViewById(R.id.am_rv)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        rvAdapter = RvAdapter(inflater, FirmListDto(getFirms()), this)
        recyclerView.adapter = rvAdapter

        val divider = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(divider)

        webView = findViewById(R.id.am_webview)
        webView.visibility = GONE
        webView.isVerticalScrollBarEnabled = false
        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = false
        webView.settings.loadWithOverviewMode = true

        webView.loadUrl("file:///android_asset/map_api.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                val js = "initMap('13933647002593772');" // The Dubai Mall

                webView.evaluateJavascript(js, ValueCallback {
                    // view.scrollTo(webView.width, 0)
                })
            }
        }


        if (savedInstanceState != null) {
            val firmId = savedInstanceState.getLong("firmId", 0)
            if (firmId != 0L) {
                webView.visibility = VISIBLE
                recyclerView.visibility = GONE

                webView.loadUrl("javascript:showFirm('$firmId')")
            }
        }
    }

    private fun getFirms() : List<FirmDto> {
        return listOf(FirmDto(70000001006522133, "Officine Panerai", "G", "Clocks / Watches / Accessories"),
                FirmDto(70000001006682661, "Hour Choice", "1", "Clocks / Watches / Accessories"),
                FirmDto(70000001033384425, "Montegrappa Italia, LLC", "2", "Clocks / Watches / Accessories"),
                FirmDto(70000001006524436, "Tissot", "G", "Clocks / Watches / Accessories"),
                FirmDto(70000001006651406, "TAG Heuer", "G", "Clocks / Watches / Accessories"),
                FirmDto(70000001031667427, "Rolex Watches", "G", "Clocks / Watches / Accessories"))
    }

    fun hideMap() {
        webView.visibility = GONE
        recyclerView.visibility = VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) {
            if (selectedFirm != null) {
                outState.putLong("firmId", selectedFirm!!.id)
            } else {
                outState.remove("firmId")
            }
        }
    }

    class RvAdapter(private val inflater: LayoutInflater,
                    private val dto: FirmListDto,
                    private val mainActivity: MainActivity) : RecyclerView.Adapter<FirmSimpleView>() {


        override fun getItemCount(): Int {
            return if (dto.items == null) {
                0
            } else dto.items!!.size
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FirmSimpleView {

            return FirmSimpleView.createView(viewGroup, inflater, dto.items[i])
        }

        override fun onBindViewHolder(view: FirmSimpleView, i: Int) {
            view.initView(dto.items[i])
            view.itemView.setOnClickListener {
                run {
                    val firmDto = dto.items[i]
                    mainActivity.selectedFirm = firmDto

                    val fm = mainActivity.supportFragmentManager
                    val detailedView = FirmDetailedViewImpl.createView(firmDto)
                    val transaction = fm.beginTransaction()
                    transaction.add(android.R.id.content, detailedView)
                    transaction.addToBackStack(null)
                    transaction.commitAllowingStateLoss()

                    mainActivity.webView.visibility = VISIBLE
                    mainActivity.recyclerView.visibility = GONE
                    mainActivity.webView.loadUrl("javascript:showFirm('${firmDto.id}')")
                }
            }
        }
    }
}
