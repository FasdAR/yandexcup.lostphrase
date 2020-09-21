package ru.fasdev.yandexlostphrase.manager.model.shape

import ru.fasdev.yandexlostphrase.manager.model.ColorShape
import ru.fasdev.yandexlostphrase.manager.model.anim.Animation

abstract class PseudoShape(open val centerX: Double, open val centerY: Double, open val color: ColorShape, open val animations: List<Animation>)