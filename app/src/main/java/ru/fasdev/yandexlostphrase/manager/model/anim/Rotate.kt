package ru.fasdev.yandexlostphrase.manager.model.anim

data class Rotate(val angle: Double, override val time: Double, override val isCycle: Boolean): Animation(time, isCycle)