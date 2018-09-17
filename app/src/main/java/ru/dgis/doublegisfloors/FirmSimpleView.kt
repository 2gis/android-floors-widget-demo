package ru.dgis.doublegisfloors

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import ru.dgis.doublegisfloors.model.FirmDto

class FirmSimpleView private constructor(itemView: View, dto: FirmDto) : RecyclerView.ViewHolder(itemView) {

    init {
        initView(dto)
    }

    fun initView(simpleDto: FirmDto) {
        val vName = this.itemView.findViewById<TextView>(R.id.fsv_name)
        val vDescription1 = this.itemView.findViewById<TextView>(R.id.fsv_description1)
        val vDescription2 = this.itemView.findViewById<TextView>(R.id.fsv_description2)

        vDescription1.visibility = View.VISIBLE
        vDescription2.visibility = View.VISIBLE

        this.itemView.tag = simpleDto
        vName.text = simpleDto.name

        setDescription(vDescription1, simpleDto.mainRubric)
        setDescription(vDescription2, simpleDto.floor + " floor")
    }

    private fun setDescription(vDescription: TextView, description: String) {
        if (description.isNullOrEmpty()) {
            vDescription.visibility = View.GONE
        } else {
            vDescription.text = description
        }
    }

    companion object {

        fun createView(parent: ViewGroup, inflater: LayoutInflater, dto: FirmDto): FirmSimpleView {
            return FirmSimpleView(inflater.inflate(R.layout.firm_simple_view, parent, false), dto)
        }
    }
}
