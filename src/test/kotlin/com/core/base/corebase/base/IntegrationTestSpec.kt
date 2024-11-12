package com.core.base.corebase.base

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
abstract class IntegrationTestSpec(body: BehaviorSpec.() -> Unit) : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var entityManager: EntityManager

    init {
        isolationMode = IsolationMode.InstancePerLeaf
        afterAny {
            entityManager.clear() // clears persistence context
            entityManager.flush() // synchronizes the database state
        }
        body()
    }
}