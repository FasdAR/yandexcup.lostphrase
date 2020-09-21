package ru.fasdev.yandexlostphrase.manager.model.shape

import ru.fasdev.yandexlostphrase.manager.model.ColorShape
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation

abstract class PseudoShape(open var centerX: Double, open var centerY: Double, open val color: ColorShape, open val animations: List<Animation>)