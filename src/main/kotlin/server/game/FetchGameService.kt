package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject

class FetchGameService @Inject constructor(
    private val objectMapper: ObjectMapper
) {
}
