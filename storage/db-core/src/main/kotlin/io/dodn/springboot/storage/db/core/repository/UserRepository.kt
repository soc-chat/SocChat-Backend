package io.dodn.springboot.storage.db.core.repository

import io.dodn.springboot.storage.db.core.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>
