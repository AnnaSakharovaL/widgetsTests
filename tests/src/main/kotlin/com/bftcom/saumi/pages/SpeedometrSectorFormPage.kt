package pages

import com.bftcom.utils.*
import org.openqa.selenium.By

object SpeedometrSectorForm : PageBuilder<SpeedometrSectorFormPage>(SpeedometrSectorFormPage::class)

class SpeedometrSectorFormPage : BaseBlock(SS(By.className("ant-modal-content")).last()) {

    val lazyClear = resettableManager()
    val fromField: TextHumanElement by resettableLazy(lazyClear) { content.find(NumberField("от")).withTitleForText("От") }
    val toField: TextHumanElement by resettableLazy(lazyClear) { content.find(NumberField("до")).withTitleForText("До") }
    val saveButton: HumanSelenideElement by resettableLazy(lazyClear) { content.find(Button("Сохранить")).withTitle("Сохранить") }

    override fun toString(): String {
        return "Форма заполнения секторов для диаграммы Спидометр"
    }
}

