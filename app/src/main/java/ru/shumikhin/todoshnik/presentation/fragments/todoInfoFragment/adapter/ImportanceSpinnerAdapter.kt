package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.utils.Importance

class ImportanceSpinnerAdapter(private val context: Context, private val impList: List<Importance>):
    ArrayAdapter<Importance>(context, 0, impList) {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null){
            view = inflater.inflate(R.layout.spinner_importance_item, parent, false)
        }
        val item = getItem(position)

        val importanceTitle = view!!.findViewById<TextView>(R.id.tv_spinner_importance_title)

        when(item!!.ordinal){
            IMPORTANCE_LOW -> {
                importanceTitle.text = ContextCompat.getString(context, R.string.importance_low)
            }
            IMPORTANCE_BASIC -> {
                importanceTitle.text = ContextCompat.getString(context, R.string.importance_basic)
            }
            IMPORTANCE_HIGH -> {
                importanceTitle.text = "!! ${ContextCompat.getString(context, R.string.importance_high)}"
                importanceTitle.setTextColor(ContextCompat.getColor(context,R.color.red))
            }
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if(view == null){
            view = inflater.inflate(R.layout.spinner_importance_dropdown_item, parent, false)
        }
        val item = getItem(position)

        val importanceTitle = view!!.findViewById<TextView>(R.id.tv_importance_title)

        when(item!!.ordinal){
            IMPORTANCE_LOW -> {
                importanceTitle.text = ContextCompat.getString(context, R.string.importance_low)
            }
            IMPORTANCE_BASIC -> {
                importanceTitle.text = ContextCompat.getString(context, R.string.importance_basic)
            }
            IMPORTANCE_HIGH -> {
                importanceTitle.text = "!! ${ContextCompat.getString(context, R.string.importance_high)}"
                importanceTitle.setTextColor(ContextCompat.getColor(context,R.color.red))
            }
        }
        return view
    }

    companion object{
        const val IMPORTANCE_LOW = 1
        const val IMPORTANCE_BASIC = 0
        const val IMPORTANCE_HIGH= 2
    }

}

