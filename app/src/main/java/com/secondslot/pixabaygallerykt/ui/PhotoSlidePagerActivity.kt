package com.secondslot.pixabaygallerykt.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.secondslot.pixabaygallerykt.R
import com.secondslot.pixabaygallerykt.data.model.PhotoItem
import com.secondslot.pixabaygallerykt.ui.viewmodel.PhotoSlidePagerViewModel

const val EXTRA_POSITION = "com.secondslot.pixabaygallerykt.position"

class PhotoSlidePagerActivity() : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var mPhotoSlidePagerViewModel: PhotoSlidePagerViewModel
    private var mPhotoItems: List<PhotoItem> = emptyList()

    companion object {

        fun newIntent(context: Context, position: Int): Intent {
            val intent = Intent(context, PhotoSlidePagerActivity::class.java)
            intent.putExtra(EXTRA_POSITION, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_photo)

        mPhotoSlidePagerViewModel = ViewModelProvider(this)
            .get(PhotoSlidePagerViewModel::class.java)

        mPhotoItems = mPhotoSlidePagerViewModel.mPhotoItemLiveData.value
            ?: emptyList()
        mPhotoSlidePagerViewModel.mPhotoItemLiveData.observe(
            this,
            { photoItems ->
                mPhotoItems = photoItems
                Log.d("myLogs", "mPhotosLiveData changed")
            }
        )

        val position = intent.getIntExtra(EXTRA_POSITION, 0)

        Log.d("myLogs", "mPhotoItems size = ${mPhotoItems.size}")
        mViewPager = findViewById(R.id.pager)
        val pagerAdapter = PhotoSlidePagerAdapter(this)
        mViewPager.apply {
            adapter = pagerAdapter
            setCurrentItem(position, false)
            offscreenPageLimit = 1
        }
    }

    /*
    Класс ViewPager adapter
    */
    private inner class PhotoSlidePagerAdapter(activity: AppCompatActivity)
        : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            Log.d("myLogs", "mPhotoItems size in adapter = ${mPhotoItems.size}")
            return mPhotoItems.size
        }

        override fun createFragment(position: Int): Fragment {
            Log.d("myLogs", "Position in adapter = $position")
            return PhotoDetailFragment.newInstance(mPhotoItems[position])
        }
    }

}