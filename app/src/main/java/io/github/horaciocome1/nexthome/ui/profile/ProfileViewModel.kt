package io.github.horaciocome1.nexthome.ui.profile

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.nexthome.data.profile.ProfileService
import io.github.horaciocome1.nexthome.data.profile.Owner
import io.github.horaciocome1.nexthome.util.ObservableViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ObservableViewModel() {

    private val service: ProfileService by lazy { ProfileService() }

    @Bindable
    val name: MutableLiveData<CharSequence> =
        MutableLiveData<CharSequence>().apply { value = "" }

    @Bindable
    val cellPhone: MutableLiveData<CharSequence> =
        MutableLiveData<CharSequence>().apply { value = "" }

    suspend fun retrieveProfile(context: CoroutineContext) = withContext(context) {
        val owner = service.retrieveProfile()
        name.value = owner?.name
        cellPhone.value = owner?.cellPhone.toString()
    }

    fun updateProfile() = viewModelScope.launch {
        val owner = Owner()
        name.value?.let { owner.name = it.toString() }
        cellPhone.value?.toString()
            ?.toIntOrNull()
            ?.let { owner.cellPhone = it }
        service.updateProfile(owner)
    }

}