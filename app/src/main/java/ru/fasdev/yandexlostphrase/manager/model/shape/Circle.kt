package ru.fasdev.yandexlostphrase.manager.model.shape

import ru.fasdev.yandexlostphrase.manager.model.ColorShape
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation

data class Circle(override val centerX: Double, override val centerY: Double, val radius: Double, override val color: ColorShape, override val animations: List<Animation>): PseudoShape(centerX, centerY, color, animations)