package ru.fasdev.yandexlostphrase.manager.model.shape

import ru.fasdev.yandexlostphrase.manager.model.ColorShape
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation

data class Circle(override var centerX: Double, override var centerY: Double, val radius: Double, override val color: ColorShape, override val animations: List<Animation>): PseudoShape(centerX, centerY, color, animations)