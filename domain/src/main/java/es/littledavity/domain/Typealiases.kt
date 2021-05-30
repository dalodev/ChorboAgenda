/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain

import es.littledavity.domain.commons.usecases.ObservableUseCase
import es.littledavity.domain.contacts.entities.*
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams

typealias ObservableContactsUseCase = ObservableUseCase<ObserveContactsUseCaseParams, List<Contact>>

typealias DomainContact = Contact
typealias DomainImage = Image
typealias DomainCreationDate = CreationDate
typealias DomainCreationDateCategory = CreationDateCategory
