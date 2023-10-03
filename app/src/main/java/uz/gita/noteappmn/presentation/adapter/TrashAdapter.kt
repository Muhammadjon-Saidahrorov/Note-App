package uz.gita.noteappmn.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.databinding.ItemNoteBinding
import uz.gita.noteappmn.utils.myApply

class TrashAdapter: ListAdapter<NoteData, TrashAdapter.Holder>(NoteCallback) {

    private var changeLongClickListener: ((NoteData) -> Unit)? = null

    inner class Holder(private val itemNoteBinding: ItemNoteBinding): RecyclerView.ViewHolder(itemNoteBinding.root) {

        init {
            itemNoteBinding.root.setOnLongClickListener{
                changeLongClickListener?.invoke(getItem(adapterPosition))
                true
            }
        }

        fun bind() = itemNoteBinding.myApply{
            val item = getItem(adapterPosition)

            title.text = item.title
            content.text = item.content.parseAsHtml()
            date.text = item.createdAt
        }
    }

    object NoteCallback: DiffUtil.ItemCallback<NoteData>(){
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind()
    }

    fun setOnChangeLongClickListener(block: (NoteData) -> Unit){
        changeLongClickListener = block
    }

}