package ru.fasdev.yandexlostphrase.manager.model.anim

data class Move(val destX: Double, val destY: Double, override val time: Double, override val isCycle: Boolean): Animation(time, isCycle)