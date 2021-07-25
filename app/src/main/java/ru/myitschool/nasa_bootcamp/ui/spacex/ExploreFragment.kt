package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.topCard.setOnClickListener {
            anim1 = animateCardToRightMargin(anim1, binding.topCard)
        }

        binding.bottomCard.setOnClickListener {
            anim2 = animateCardToRightMargin(anim2, binding.bottomCard)
        }

        binding.capsulesCard.setOnClickListener {
            anim3 = animateCardToRightMargin(anim3, binding.capsulesCard)
        }

        binding.coresCard.setOnClickListener {
            anim4 = animateCardToRightMargin(anim4, binding.coresCard)
        }

        binding.dragonsCard.setOnClickListener {
            anim5 = animateCardToRightMargin(anim5, binding.dragonsCard)
        }

        binding.launchCard.setOnClickListener {
            anim6 = animateCardToRightMargin(anim6, binding.launchCard)
        }

        loadImage(requireContext(), STARMAN_GIF_LINK, binding.starmanGif)

        return binding.root
    }

    fun animateCardToRightMargin(anim: HOR_ANIMS, view: View): HOR_ANIMS {

        when {
            anim == HOR_ANIMS.NONE -> {
                animateIt {
                    animate(view) animateTo {
                        marginRight(48f)
                    }
                }.start()

                return HOR_ANIMS.RIGHT
            }

            anim == HOR_ANIMS.RIGHT -> {
                animateIt {
                    animate(view) animateTo {
                        marginRight(8f)
                    }
                }.start()

                return HOR_ANIMS.NONE
            }
        }
        return HOR_ANIMS.NONE
    }
}