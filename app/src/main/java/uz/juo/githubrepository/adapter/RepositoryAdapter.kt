package uz.juo.githubrepository.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.juo.githubrepository.R
import uz.juo.githubrepository.databinding.ReposItemBinding
import uz.juo.githubrepository.models.Item

class RepositoryAdapter(var context: Context,var setOnClick: itemClick) :
    PagingDataAdapter<Item, RepositoryAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var item: ReposItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(data: Item, position: Int) {
            item.itemName.text = data.full_name
           var language = if (data.language==null){
               "Language not found"
           }else{
               data.language
           }
            item.languageTv.text ="Language :  $language"
            Glide.with(context).load(data.owner.avatar_url).placeholder(R.drawable.github).into(item.itemImage)
            item.root.setOnClickListener {
                setOnClick.setOnClick(data, position)
            }
            var anim = AnimationUtils.loadAnimation(context, R.anim.rv)
            item.root.startAnimation(anim)
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ReposItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class MyDiffUtill : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    interface itemClick {
        fun setOnClick(data: Item, position: Int)
    }
}