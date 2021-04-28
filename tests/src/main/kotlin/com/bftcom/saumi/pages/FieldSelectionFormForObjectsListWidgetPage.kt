package pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object FieldSelectionFormForObjectsListWidget : PageBuilder<FieldSelectionFormForObjectsListWidgetPage>(FieldSelectionFormForObjectsListWidgetPage::class)

class FieldSelectionFormForObjectsListWidgetPage : BaseBlock(SS(By.className("ant-modal-content")).last()),
    ListTableInterface by ListSection(PaginationSection()) {

    val lazyClear = resettableManager()
    val fieldNameField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Поле")).withTitleForList("Поле")
    }
    val saveButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(Button("Сохранить")).withTitle("Сохранить")
    }

    override fun toString(): String {
        return "Форма заполнения полей для виджета Список объектов"
    }
}

