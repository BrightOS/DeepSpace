package ru.myitschool.nasa_bootcamp.ui.nasa.pages

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment.ImageOfDayFragment

class NasaPagerAdapter(private val context: Context, manager: FragmentManager?) :
    FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        var type = "APOD"
        when(position){
            0 -> type = "APOD"
            1 -> type = "News"
            2 -> type = "Blogs"
            3 -> type = "Popular"
        }
        return ActionFragment.newInstance(type)
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Home"
            1 -> return "News"
            2 -> return "Blogs"
            3 -> return "Popular"
        }
        Log.e("PagerAdapter getItem", "Error at position$position")
        return "Home"
    }

}