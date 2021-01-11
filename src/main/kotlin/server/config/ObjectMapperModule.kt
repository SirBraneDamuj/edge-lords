package server.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides

@Module
class ObjectMapperModule {
    @Provides
    fun provideObjectMapper() =
        ObjectMapper()
            .registerKotlinModule()
}
