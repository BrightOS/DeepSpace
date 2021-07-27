package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.ViewCompat.animate
import androidx.navigation.fragment.findNavController
import ru.myitschool.nasa_bootcamp.databinding.FragmentExploreBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.STARMAN_GIF_LINK
import ru.myitschool.nasa_bootcamp.utils.loadImage


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    enum class HOR_ANIMS {
        RIGHT, LEFT, NONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)


        var anim1 = HOR_ANIMS.NONE
        var anim2 = HOR_ANIMS.NONE
        var anim3 = HOR_ANIMS.NONE
        var anim4 = HOR_ANIMS.NONE
        var anim5 = HOR_ANIMS.NONE
        var anim6 = HOR_ANIMS.NONE


        anim1 = animateCardToRightMargin(anim1, binding.historyCard, binding.historyButton)
        anim1 = animateCardToRightMargin(anim1, binding.historyCard, binding.historyButton)
        anim2 = animateCardToRightMargin(anim2, binding.roadsterCard, binding.roadsterButton)
        anim2 = animateCardToRightMargin(anim2, binding.roadsterCard, binding.roadsterButton)
        anim3 = animateCardToRightMargin(anim3, binding.capsulesCard, binding.capsulesButton)
        anim3 = animateCardToRightMargin(anim3, binding.capsulesCard, binding.capsulesButton)
        anim4 = animateCardToRightMargin(anim4, binding.coresCard, binding.coresButton)
        anim4 = animateCardToRightMargin(anim4, binding.coresCard, binding.coresButton)
        anim5 = animateCardToRightMargin(anim5, binding.dragonsCard, binding.dragonsButton)
        anim5 = animateCardToRightMargin(anim5, binding.dragonsCard, binding.dragonsButton)
        anim6 = animateCardToRightMargin(anim6, binding.launchCard, binding.launchesButton)
        anim6 = animateCardToRightMargin(anim6, binding.launchCard, binding.launchesButton)

        binding.historyCard.setOnClickListener {
            anim1 = animateCardToRightMargin(anim1, binding.historyCard, binding.historyButton)
        }

        binding.roadsterCard.setOnClickListener {
            anim2 = animateCardToRightMargin(anim2, binding.roadsterCard, binding.roadsterButton)
        }

        binding.capsulesCard.setOnClickListener {
            anim3 = animateCardToRightMargin(anim3, binding.capsulesCard, binding.capsulesButton)
        }

        binding.coresCard.setOnClickListener {
            anim4 = animateCardToRightMargin(anim4, binding.coresCard, binding.coresButton)
        }

        binding.dragonsCard.setOnClickListener {
            anim5 = animateCardToRightMargin(anim5, binding.dragonsCard, binding.dragonsButton)
        }

        binding.launchCard.setOnClickListener {
            anim6 = animateCardToRightMargin(anim6, binding.launchCard, binding.launchesButton)
        }

        loadImage(requireContext(), STARMAN_GIF_LINK, binding.starmanGif)


        val navController = findNavController()

        binding.historyButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToHistoryFragment()
            navController.navigate(action)
        })

        binding.roadsterButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToRoadsterFragment()
            navController.navigate(action)
        })

        binding.dragonsButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToDragonsFragment()
            navController.navigate(action)
        })

        binding.coresButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToCoresFragment()
            navController.navigate(action)
        })

        binding.capsulesButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToCapsulesFragment()
            navController.navigate(action)
        })

        binding.launchesButton.setOnClickListener(View.OnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToLaunchLandFragment()
            navController.navigate(action)
        })

        return binding.root
    }


    fun animateCardToRightMargin(anim: HOR_ANIMS, view: View, btn: ImageButton): HOR_ANIMS {

        when {
            anim == HOR_ANIMS.NONE -> {
                animateIt {
                    animate(view) animateTo {
                        marginRight(48f)
                    }
                    animate(btn) animateTo {
                        visible()
                        Log.d("Em", "DoneDoneDone")
                    }

                }.start()

                return HOR_ANIMS.RIGHT
            }

            anim == HOR_ANIMS.RIGHT -> {
                animateIt {
                    animate(view) animateTo {
                        marginRight(8f)
                    }

                    animate(btn) animateTo {
                        invisible()
                    }
                }.start()

                return HOR_ANIMS.NONE
            }
        }

        return HOR_ANIMS.NONE
    }
}