package com.example.todonoteapp.common

import android.os.Build
import android.view.View
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todonoteapp.R
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.presentation.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.todonoteapp.data.model.NotePriority

object BindingAdapter {

    @BindingAdapter("android:navigateToAddFragment")
    @JvmStatic
    fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
        view.setOnClickListener {
            if (navigate) view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        when (emptyDatabase.value) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.INVISIBLE
            else -> {  }
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: NotePriority) {
        when (priority) {
            NotePriority.HIGH -> view.setSelection(0)
            NotePriority.MEDIUM -> view.setSelection(1)
            NotePriority.LOW -> view.setSelection(2)
            else -> NotePriority.NONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(view: CardView, priority: NotePriority) {
        when (priority) {
            NotePriority.HIGH -> view.setCardBackgroundColor(view.context.getColor(R.color.red))
            NotePriority.MEDIUM -> view.setCardBackgroundColor(view.context.getColor(R.color.yellow))
            NotePriority.LOW -> view.setCardBackgroundColor(view.context.getColor(R.color.green))
            else -> NotePriority.NONE
        }
    }

    @BindingAdapter("android:sendDataToUpdateFragment")
    @JvmStatic
    fun sendDataToUpdateFragment(view: CardView, noteData: NoteData) {
        view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(noteData)
            view.findNavController().navigate(action)
        }
    }

}

