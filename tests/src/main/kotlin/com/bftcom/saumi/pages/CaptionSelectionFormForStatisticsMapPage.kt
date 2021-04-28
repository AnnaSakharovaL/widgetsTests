package pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object CaptionSelectionFormForStatisticsMap : PageBuilder<CaptionSelectionFormForStatisticsMapPage>(CaptionSelectionFormForStatisticsMapPage::class)

class CaptionSelectionFormForStatisticsMapPage : BaseBlock(SS(By.className("ant-modal-content")).last()),
    ListTableInterface by ListSection(PaginationSection()) {

    val lazyClear = resettableManager()
    val captionField: TextHumanElement by resettableLazy(lazyClear) {
        content.find(TextField("Подпись")).withTitleForText("Подпись")
    }
    val valueField: ListHumanElement by resettableLazy(lazyClear) {
        content.find(ListField("Значение")).withTitleForList("Значение")
    }
    val saveButton: HumanSelenideElement by resettableLazy(lazyClear) {
        content.find(Button("Сохранить")).withTitle("Сохранить")
    }

    override fun toString(): String {
        return "Форма заполнения подписей для карты статистики"
    }
}

