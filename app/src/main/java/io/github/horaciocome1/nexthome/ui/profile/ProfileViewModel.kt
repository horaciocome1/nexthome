package io.github.horaciocome1.nexthome.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.nexthome.data.profile.ProfileService
import io.github.horaciocome1.nexthome.data.profile.Owner
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel() {

    private val service: ProfileService by lazy { ProfileService() }

    val name: MutableLiveData<CharSequence> = MutableLiveData()

    val cellPhone: MutableLiveData<CharSequence> = MutableLiveData()

    suspend fun retrieveProfile(context: CoroutineContext) = withContext(context) {
        val proprietario = service.retrieveProfile()
        name.value = proprietario?.name
        cellPhone.value = proprietario?.cellPhone.toString()
    }

    fun updateProfile() = viewModelScope.launch {
        val proprietario = Owner()
        name.value?.let { proprietario.name = it.toString() }
        cellPhone.value?.toString()
            ?.toIntOrNull()
            ?.let { proprietario.cellPhone = it }
        service.updateProfile(proprietario)
    }

}