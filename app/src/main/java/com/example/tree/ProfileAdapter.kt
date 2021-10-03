package com.example.tree

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tree.All_List.GetModel_all_list

class ProfileAdapter(private val context: Context) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    var datas = mutableListOf<GetModel_all_list>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.all_ranking_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.all_nikname)
        private val txttreecount: TextView = itemView.findViewById(R.id.all_tree_count)
        private val txtRank:TextView=itemView.findViewById(R.id.all_rank_num)

        fun bind(item: GetModel_all_list) {
            txtName.text = item.name
            txttreecount.text = item.treeCount.toString()
            txtRank.text=item.rank.toString()

        }
    }


}