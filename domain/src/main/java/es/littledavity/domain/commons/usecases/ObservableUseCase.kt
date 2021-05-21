/*
 * Copyright 2021 dev.id
 */
package es.littledavity.domain.commons.usecases

import kotlinx.coroutines.flow.Flow

interface ObservableUseCase<In, Out> : UseCase<In, Flow<Out>>
