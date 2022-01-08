package ru.berserkers.deepspace.ui.asteroid_radar

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.AsteroidModel

/*
 * @author Samuil Nalisin
 */
interface AsteroidRadarViewModel {
    suspend fun getAsteroidList()
    fun getAsteroidListViewModel(): MutableLiveData<ArrayList<AsteroidModel>>
    fun getViewModelScope(): CoroutineScope
}
