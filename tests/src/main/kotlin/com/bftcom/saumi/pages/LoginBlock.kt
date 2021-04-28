package pages

import com.bftcom.saumi.apis.User
import com.bftcom.utils.*
import com.codeborne.selenide.Condition
import io.qameta.allure.Step
import org.openqa.selenium.By

object Login : PageBuilder<LoginBlock>(LoginBlock::class)

class LoginBlock : BaseBlock(By.className("login-form")) {

    lateinit var loginInput: TextByUserHumanElement
    lateinit var passwordInput: TextByUserHumanElement
    lateinit var okButton: HumanSelenideElement

    init {
        initBlock {
            loginInput = content.find(By.tagName("input")).withTitleForTextUser("для ввода логина пользователя")
            passwordInput = content.findAll(By.tagName("input"))[1].withTitleForTextUser("для ввода пароля пользователя")
            okButton = content.find(By.className("login-button")).withTitle("Войти")
        }
    }

    @Step("Вошли в систему пользователем '{0}'")
    fun auth(user: User) {
        auth(user.login, user.password)
    }

    @Step("Вошли в систему пользователем '{0}', с паролем '{1}'")
    fun auth(loginValue: String, passwordValue: String) {
        nowIs(Condition.appear)
        loginInput.textFillingLikeUser(loginValue)
        passwordInput.textFillingLikeUser(passwordValue)
        okButton.click()
        nowIsNot(Condition.exist)
    }

    override fun toString(): String {
        return "Компонент входа в систему"
    }
}