package com.bftcom.saumi.pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object ObjectsApp : PageBuilder<ObjectsAppBlock>(ObjectsAppBlock::class)

class ObjectsAppBlock : BaseBlock(By.className("page-content")) {

    lateinit var objectTree: HumanSelenideElement

    init {
        initBlock {
            objectTree = content.find(By.className("objects-tree")).withTitle("для отображения Дерева объектов")
        }
    }

    override fun toString(): String {
        return "Объекты приложения"
    }
}
