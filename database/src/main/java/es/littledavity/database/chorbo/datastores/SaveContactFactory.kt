package es.littledavity.database.chorbo.datastores

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.data.contacts.DataContact
import es.littledavity.database.chorbo.entities.Contact
import javax.inject.Inject

internal interface SaveContactFactory {
    fun createContact(contact: DataContact): Contact
}

@BindType
internal class SaveContactFactoryImpl @Inject constructor(
    private val contactMapper: ContactMapper
) : SaveContactFactory {

    override fun createContact(contact: DataContact) = contactMapper.mapToDatabaseContact(contact)
}