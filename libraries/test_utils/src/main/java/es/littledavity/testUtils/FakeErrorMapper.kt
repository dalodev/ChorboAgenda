package es.littledavity.testUtils

import es.littledavity.core.mapper.ErrorMapper

class FakeErrorMapper : ErrorMapper {


    override fun mapToMessage(error: Throwable): String {
        return "error"
    }


}