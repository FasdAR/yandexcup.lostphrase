package ru.fasdev.yandexlostphrase.manager.model

import ru.fasdev.yandexlostphrase.manager.model.anim.Animation
import ru.fasdev.yandexlostphrase.manager.model.shape.PseudoShape

data class LostPhrase(val width: Double, val height: Double, val figuresCount: Int, val shapes: List<PseudoShape>)