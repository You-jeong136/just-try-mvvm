package com.study.aos.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.aos.mvvm.databinding.ItemDiaryBinding
import com.study.aos.mvvm.domain.Diary

class DiariesAdapter(
    private val onDiaryClick: ((Diary) -> Unit)? = null
) : ListAdapter<Diary, DiariesAdapter.ViewHolder>(DiariesComparator()) {

    //1.view holder
    //2. diff util
    //3. listAdapter : notifyData 할 필요도 없고, 나름 편함. 장점은 내가 좀더 공부하기로

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDiaryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memo = getItem(position)
        holder.bind(memo, onDiaryClick)
    }

    class ViewHolder(
        private val binding: ItemDiaryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary, onDiaryClick: ((Diary) -> Unit)? = null) {
            binding.diary = diary
            binding.root.setOnClickListener { onDiaryClick?.invoke(diary) }
        }
    }

    //DiffUtil 단점 : 각 아이템마다 다 만들어줘야함. 복잡한 상태라면, 혹은 리스트 순서 등 조작을 해야한다면 직접 수동으로 해야할지도
    private class DiariesComparator : DiffUtil.ItemCallback<Diary>() {

        //item이 서로 다른지 같은지 확인하는 용...
        //(items)를 먼저 호출하고 이게 true이면 이중 확인을 위해 (content)를 호출함
        override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem.id == newItem.id
            //(고유 값으로 비교)
        }

        override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem == newItem
            // (이후 내용이 수정된 경우가 있을 수 있기에, 객체 자체를 다시 비교 )
        }
    }
}