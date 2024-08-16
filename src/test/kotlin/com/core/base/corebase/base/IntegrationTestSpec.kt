package com.core.base.corebase.base

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

@SpringBootTest
@AutoConfigureMockMvc
abstract class IntegrationTestSpec(body: BehaviorSpec.() -> Unit) : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    init {
        isolationMode = IsolationMode.InstancePerLeaf
        afterAny {
            mongoTemplate.collectionNames.forEach {
                mongoTemplate.dropCollection(it)
            }
        }
        body()
    }

}