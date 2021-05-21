package es.littledavity.domain

import es.littledavity.domain.commons.usecases.ObservableUseCase
import es.littledavity.domain.contacts.Contact
import es.littledavity.domain.contacts.commons.ObserveContactsUseCaseParams

typealias ObservableContactsUseCase = ObservableUseCase<ObserveContactsUseCaseParams, List<Contact>>

typealias DomainContact = Contact
