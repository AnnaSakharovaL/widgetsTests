package com.bftcom.utils

import com.codeborne.selenide.*
import com.codeborne.selenide.Condition.disappear
import com.codeborne.selenide.Condition.enabled
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.By.className
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

open class PageBuilder<T : BaseBlock>(val clsRef: KClass<*>) {

    operator fun invoke(condition: Condition /*= Condition.appear*/, builder: T.() -> Unit): T {
        val page = insts.get().getOrPut(clsRef, { create() as Any }) as T
        page.nowIs(condition)
        builder(page)
        return page
    }

    operator fun invoke(builder: T.() -> Unit): T {
        val page = insts.get().getOrPut(clsRef, { create() as Any }) as T
        builder(page)
        return page
    }

    operator fun invoke(): T {
        val page = insts.get().getOrPut(clsRef, { create() as Any }) as T
        return page
    }

    private fun create(): T = create(clsRef) as T

    private fun create(classRef: KClass<*>): Any? = classRef.java.newInstance()

    companion object {
        var insts: ThreadLocal<MutableMap<KClass<*>, Any>> = object : ThreadLocal<MutableMap<KClass<*>, Any>>() {
            override fun initialValue(): MutableMap<KClass<*>, Any> {
                return mutableMapOf()
            }
        }
    }
}

abstract class BaseBlock(var content: SelenideElement) {

    constructor(byCondition: By) : this(S(byCondition)) {

    }

    constructor(cssCelector: String) : this(S(cssCelector)) {

    }

    constructor(selenideElement: HumanSelenideElement) : this(S(selenideElement)) {

    }

}

@Step("Инициализация блока '{0}'")
fun <T> T.initBlock(block: T.() -> Unit) {
    block(this)
}

@Step("Проверяем, что блок '{0}' сейчас отображается")
fun <T : BaseBlock> T.nowIs(condition: Condition = Condition.appear): T {
    content.should(condition)
    return this
}

@Step("Проверяем, что блок '{0}' сейчас не отображается")
fun <T : BaseBlock> T.nowIsNot(condition: Condition = Condition.appear): T {
    content.should(Condition.not(condition))
    return this
}

@Step("Проверяем, что '{1}'")
fun <T : BaseBlock> T.checkIn(text: String, block: T.() -> Unit): T {
    block()
    return this
}

@Step("Открытие урла '{0}'")
fun openUrl(relativeOrAbsoluteUrl: String) {
    Selenide.open(relativeOrAbsoluteUrl)
    waitLoadJs()
}

@Step("Ожидание загрузки js")
fun waitLoadJs() {
    if (!S("#root").find(className("ant-spin")).exists()) {
        try {
            S("#root").find(className("ant-spin")).waitUntil(enabled, 15000)
        } catch (e: Exception) {
        }
    } else {
        S("#root").find(className("ant-spin")).waitUntil(disappear, 5000)
    }
}

sealed class ElementType(val text: String)

class TextField(labelText: String) : ElementType(labelText)
class NumberField(labelText: String) : ElementType(labelText)
class ListField(labelText: String) : ElementType(labelText)
class MultiValuedListField(labelText: String) : ElementType(labelText)
class ObjectListSeleniumField(labelText: String) : ElementType(labelText)
class ObjectField(labelText: String) : ElementType(labelText)
class MultiValuedObjectField(labelText: String) : ElementType(labelText)
class BooleanField(labelText: String) : ElementType(labelText)
class Button(labelText: String) : ElementType(labelText)
class ButtonInFieldGroup(labelText: String) : ElementType(labelText)
class TextAreaField(labelText: String) : ElementType(labelText)
class SwitchButton(labelText: String) : ElementType(labelText)

sealed class HumanSelenideElement(val human: String, b: SelenideElement) : SelenideElement by b

class BaseHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class TextHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class TextByUserHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class ListHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class ObjectHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class ObjectListHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)
class BooleanHumanElement(human: String, b: SelenideElement) : HumanSelenideElement(human, b)

class ExpandSelenideElement(b: SelenideElement) : SelenideElement by b {
    @Step("Раскрыть элемент")
    fun expand() {
        find(".ant-table-row-expand-icon")?.shouldBe(Condition.appear)?.click()
    }
}

fun SelenideElement.withTitle(human: String) = BaseHumanElement(human, this)
fun SelenideElement.withTitleForText(human: String) = TextHumanElement(human, this)
fun SelenideElement.withTitleForTextUser(human: String) = TextByUserHumanElement(human, this)
fun SelenideElement.withTitleForList(human: String) = ListHumanElement(human, this)
fun SelenideElement.withTitleForObject(human: String) = ObjectHumanElement(human, this)
fun SelenideElement.withTitleForObjectList(human: String) = ObjectListHumanElement(human, this)
fun SelenideElement.withTitleForBoolean(human: String) = BooleanHumanElement(human, this)

class ResettableLazyManager {
    // we synchronize to make sure the timing of a reset() call and new inits do not collide
    val managedDelegates = LinkedList<Resettable>()

    fun register(managed: Resettable) {
        synchronized(managedDelegates) {
            managedDelegates.add(managed)
        }
    }

    fun reset() {
        synchronized(managedDelegates) {
            managedDelegates.forEach { it.reset() }
            managedDelegates.clear()
        }
    }
}

interface Resettable {
    fun reset()
}

class ResettableLazy<PROPTYPE>(val manager: ResettableLazyManager, val init: () -> PROPTYPE) : Resettable {
    @Volatile
    var lazyHolder = makeInitBlock()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): PROPTYPE {
        return lazyHolder.value
    }

    override fun reset() {
        lazyHolder = makeInitBlock()
    }

    fun makeInitBlock(): Lazy<PROPTYPE> {
        return lazy {
            manager.register(this)
            init()
        }
    }
}

fun <PROPTYPE> resettableLazy(manager: ResettableLazyManager, init: () -> PROPTYPE): ResettableLazy<PROPTYPE> {
    return ResettableLazy(manager, init)
}

fun resettableManager(): ResettableLazyManager = ResettableLazyManager()