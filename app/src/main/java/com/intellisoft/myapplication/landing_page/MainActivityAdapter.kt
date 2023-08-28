package com.intellisoft.myapplication.landing_page

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.chat.Chat
import com.intellisoft.myapplication.data_class.DbChat
import com.intellisoft.myapplication.data_class.DbNCDs
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import org.w3c.dom.Text

class MainActivityAdapter(
    private var dbChatList: ArrayList<DbNCDs>,
    private val context: Context
    ): RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view),
    View.OnClickListener{

        val tvNcdName: TextView
        val imageView: ImageView

        init {
            tvNcdName = view.findViewById(R.id.tvNcdName)
            imageView = view.findViewById(R.id.imageView)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            val pos = adapterPosition
            val ncdName = dbChatList[pos].imageName
            val imageResource = dbChatList[pos].imageResource

            FormatterClassHelper().saveSharedPreference(context, "searchSubject", ncdName)
            FormatterClassHelper().saveSharedPreference(context, "searchSubjectImage", imageResource.toString())

            val intent = Intent(context, Chat::class.java)
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dbChatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageResource = dbChatList[position].imageResource
        val imageName = dbChatList[position].imageName

        holder.tvNcdName.text = imageName
        holder.imageView.setImageResource(imageResource)

    }


}