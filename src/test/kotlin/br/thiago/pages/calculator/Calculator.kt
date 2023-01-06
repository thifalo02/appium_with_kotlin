package br.thiago.pages.calculator

import br.thiago.commons.Actions
import br.thiago.commons.BuildPages
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidBy
import io.appium.java_client.pagefactory.AndroidFindAll
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy

class Calculator : BuildPages() {

    private val actions: Actions = Actions()

    @AndroidFindBy(id = "com.android.calculator2:id/op_add")
    @iOSXCUITFindBy(id = "")
    private lateinit var btnSum: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/op_sub")
    private lateinit var btnSub: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/op_mul")
    private lateinit var btnMult: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/op_div")
    private lateinit var btnDiv: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/del")
    private lateinit var btnDel: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/eq")
    private lateinit var btnEq: MobileElement

    @AndroidFindBy(id = "com.android.calculator2:id/result")
    private lateinit var txtResult: MobileElement

    @AndroidFindAll(value = [AndroidBy(className = "android.widget.Button")])
    private lateinit var btnNumbers: ArrayList<MobileElement>

    fun sum(num1: Int, num2: Int): String {
        actions.click(btnNumbers.first { el -> el.text.equals(num1.toString()) })
        actions.click(btnSum)
        actions.click(btnNumbers.first { el -> el.text.equals(num2.toString()) })
        actions.click(btnEq)
        return actions.getText(txtResult)
    }
}