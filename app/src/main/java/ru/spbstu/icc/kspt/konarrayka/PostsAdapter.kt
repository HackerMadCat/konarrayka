package ru.spbstu.icc.kspt.konarrayka

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
import ru.spbstu.icc.kspt.configuration.BuilderActivity


class PostsAdapter(val posts: ArrayList<String> ) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val firstName : TextView = itemView.findViewById(R.id.firstName)
        init {
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, BuilderActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }}

    override fun getItemCount() = posts.size

    fun onClick(v: View) {
        v.context.startActivity(Intent(v.context, BuilderActivity::class.java))
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text = posts[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_posts, parent,false)
        return  ViewHolder(view)
    }



}


