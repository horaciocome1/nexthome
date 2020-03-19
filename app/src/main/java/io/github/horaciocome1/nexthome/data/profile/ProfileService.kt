package io.github.horaciocome1.nexthome.data.profile

class ProfileService : ProfileInterface {

    override suspend fun retrieveProfile(): Proprietario {
        return Proprietario(name = "AK Mobiliária")
    }

    override suspend fun updateProfile(proprietario: Proprietario): Boolean {
        TODO("Not yet implemented")
    }

}