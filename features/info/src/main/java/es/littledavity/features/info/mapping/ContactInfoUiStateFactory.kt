/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.widgets.ContactInfoUiState
import javax.inject.Inject

internal interface ContactInfoUiStateFactory {
    fun createWithEmptyState(): ContactInfoUiState
    fun createWithLoadingState(): ContactInfoUiState
    fun createWithResultState(
        contact: Contact,
        isLiked: Boolean
    ): ContactInfoUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactInfoUiStateFactoryImpl @Inject constructor(
    private val modelFactory: ContactInfoModelFactory
) : ContactInfoUiStateFactory {

    override fun createWithEmptyState() = ContactInfoUiState.Empty

    override fun createWithLoadingState() = ContactInfoUiState.Loading

    override fun createWithResultState(contact: Contact, isLiked: Boolean) = ContactInfoUiState.Result(
        modelFactory.createInfoModel(contact, isLiked)
    )
}
