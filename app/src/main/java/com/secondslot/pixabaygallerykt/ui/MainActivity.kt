package com.secondslot.pixabaygallerykt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.secondslot.pixabaygallerykt.R
import com.secondslot.pixabaygallerykt.ui.adapter.PhotoAdapter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), PhotoAdapter.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Если фрагмент не подключен, подключаем его
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, GalleryFragment.newInstance())
                .commit()
        }
    }

    override fun onPhotoSelected(position: Int) {
        val intent = PhotoSlidePagerActivity.newIntent(this, position)
        startActivity(intent)
    }
}