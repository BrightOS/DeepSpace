package ru.berserkers.deepspace.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.InfoModel
import ru.berserkers.deepspace.data.repository.SpaceXRepository
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl  @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel(), InfoViewModel {

    var info : MutableLiveData<InfoModel> = MutableLiveData<InfoModel>()

    override suspend fun getInfo() {
        val response = repository.getInfo()
        info.value = response.body()!!.createInfoModel()
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope

    override fun getInfoLiveData(): MutableLiveData<InfoModel> {
        return info
    }
}
