package pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object TableSelectionForm : PageBuilder<TableSelectionFormInWidgetPage>(TableSelectionFormInWidgetPage::class)

class TableSelectionFormInWidgetPage : BaseBlock(SS(By.className("ant-modal-content")).last()),
    ListTableInterface by ListSection(PaginationSection()) {

    val lazyClear = resettableManager()
    val appObjectField: ObjectHumanElement by resettableLazy(lazyClear) {
        content.find(ObjectField("Объект приложения")).withTitleForObject("Объект приложения")
    }
    val saveButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(Button("Сохранить")).withTitle("Сохранить")
    }

    override fun toString(): String {
        return "Форма заполнения таблицы со списком объектов"
    }
}

