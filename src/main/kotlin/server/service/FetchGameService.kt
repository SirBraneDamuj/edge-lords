package server.service

import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject

class FetchGameService @Inject constructor(
    private val objectMapper: ObjectMapper
) {
}
