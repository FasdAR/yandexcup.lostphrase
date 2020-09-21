package ru.fasdev.yandexlostphrase.manager.model.anim

data class Scale(val destScale: Double, override val time: Double, override val isCycle: Boolean): Animation(time, isCycle)