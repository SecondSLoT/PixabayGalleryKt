package com.secondslot.pixabaygallerykt.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.secondslot.pixabaygallerykt.R
import com.secondslot.pixabaygallerykt.ui.adapter.EmptyAdapter
import com.secondslot.pixabaygallerykt.ui.adapter.PhotoAdapter
import com.secondslot.pixabaygallerykt.ui.viewmodel.GalleryViewModel

private const val TAG = "GalleryFragment"

class GalleryFragment : Fragment() {

    private lateinit var mGalleryViewModel: GalleryViewModel
    private lateinit var mPhotoRecyclerView: RecyclerView

    companion object {
        fun newInstance() = GalleryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключаем ViewModel к фрагменту
        mGalleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        // Инициализируем RecyclerView
        mPhotoRecyclerView = view.findViewById(R.id.photo_recycler_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Подключаем observer для LiveData с фотками
        mGalleryViewModel.mPhotoItemLiveData.observe(
            viewLifecycleOwner,
            { photoItems ->
                if (photoItems.isEmpty()) {
                    mPhotoRecyclerView.layoutManager = LinearLayoutManager(context)
                    mPhotoRecyclerView.adapter = EmptyAdapter()
                } else {
                    mPhotoRecyclerView.adapter = PhotoAdapter(photoItems)
                    mPhotoRecyclerView.layoutManager = GridLayoutManager(context, 3)
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_gallery, menu)

        // Получаем ссылку на SearchView
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    mGalleryViewModel.fetchPhotos(query)
                    // После ввода запроса скрываем клавиатуру
                    val imm = activity?.getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText == "") {
                        mGalleryViewModel.fetchPhotos()
                    }
                    return true
                }
            })
        }
    }
}