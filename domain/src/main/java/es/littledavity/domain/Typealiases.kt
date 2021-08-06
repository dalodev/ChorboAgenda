/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain

import es.littledavity.domain.commons.usecases.ObservableUseCase
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.CreationDate
import es.littledavity.domain.contacts.entities.CreationDateCategory
import es.littledavity.domain.contacts.entities.Image
import es.littledavity.domain.contacts.entities.Info

typealias ObservableContactsUseCase = ObservableUseCase<ObserveContactsUseCaseParams, List<Contact>>

typealias DomainContact = Contact
typealias DomainImage = Image
typealias DomainCreationDate = CreationDate
typealias DomainCreationDateCategory = CreationDateCategory
typealias DomainInfo = Info
