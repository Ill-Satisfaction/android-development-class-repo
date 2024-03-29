package com.example.todomkvi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todomkvi.R
import com.example.todomkvi.data.ContentType
import com.example.todomkvi.data.Tidbit
import com.example.todomkvi.recyclers.TidbitAdapter
import com.example.todomkvi.repositories.CollectionRepo
import com.example.todomkvi.repositories.TidbitRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        //val textView: TextView = root.findViewById(R.id.section_label)

        pageViewModel.text.observe(this, Observer<String> {
            //textView.text = it
        })

        // setup recycler
        // get id of thing
        val name = "today"
        // get list from id
        val list = ArrayList<Tidbit>()
        GlobalScope.launch {
            CollectionRepo.getCollectionWithTidbitsByName(name)?.tidbits?.let { list.addAll(it) }
        }

        val rv = root.findViewById<RecyclerView>(R.id.rv_fragment)
        val rvAdapter = TidbitAdapter(list)

        rv!!.apply {
            layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
            adapter = rvAdapter
        }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}