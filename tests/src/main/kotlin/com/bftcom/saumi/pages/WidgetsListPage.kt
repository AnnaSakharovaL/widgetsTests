package pages

import com.bftcom.utils.*
import com.codeborne.selenide.Selectors.byClassName

object WidgetsList : PageBuilder<WidgetsListPage>(WidgetsListPage::class)

class WidgetsListPage : BaseBlock(S(byClassName("objects-tree"))),
    ActionsInWidgetsMenuInterface by ActionsInWidgetsMenuBlockSection("Виджеты") {

    override fun toString(): String {
        return "Блок списка виджетов в админке"
    }
}
