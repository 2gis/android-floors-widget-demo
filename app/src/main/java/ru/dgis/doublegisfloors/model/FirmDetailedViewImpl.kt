package ru.dgis.doublegisfloors.model

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.dgis.doublegisfloors.GsonSerializer
import ru.dgis.doublegisfloors.MainActivity
import ru.dgis.doublegisfloors.R


class FirmDetailedViewImpl : Fragment() {
    private lateinit var dto: FirmDto
    private lateinit var mainView: ViewGroup

    private val serializer = GsonSerializer()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (savedInstanceState != null) {
            dto = serializer.fromJson(savedInstanceState.getString("dto"), FirmDto::class.java)
        }

        this.mainView = inflater.inflate(R.layout.firm_detailed_view, container, false) as ViewGroup

        this.mainView.findViewById<View>(R.id.fdv_back_button)
                .setOnClickListener { activity!!.supportFragmentManager.popBackStack() }

        this.mainView.findViewById<TextView>(R.id.fdv_name).text = dto.name
        this.mainView.findViewById<TextView>(R.id.fdv_main_rubric).text = dto.mainRubric
        this.mainView.findViewById<TextView>(R.id.fdv_floor).text = dto.floor + " floor"

        return this.mainView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
        outState.putString("dto", serializer.toJson(dto))
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideMap()
    }

    companion object {

        fun createView(dto: FirmDto): FirmDetailedViewImpl {
            val result = FirmDetailedViewImpl()
            result.dto = dto

            return result
        }
    }
}
