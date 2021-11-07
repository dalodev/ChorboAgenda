/*
 * Copyright 2021 dalodev
 */
package es.littledavity.domain.commons.usecases

import kotlinx.coroutines.flow.Flow

interface ObservableUseCase<In, Out> : UseCase<In, Flow<Out>>
