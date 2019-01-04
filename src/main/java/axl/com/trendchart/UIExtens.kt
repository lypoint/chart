package axl.com.trendchart

import android.app.Activity
import android.app.Dialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.io.IOException


fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

fun Context.sp2px(spValue: Int): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

fun Fragment.dip(value: Int): Int = activity?.dip(value) ?: 0

fun Fragment.dip(value: Float): Int = activity?.dip(value) ?: 0

fun Fragment.sp2px(value: Int): Int = activity?.sp2px(value) ?: 0

fun Fragment.sp2px(value: Float): Int = activity?.sp2px(value) ?: 0


fun Fragment.addFragment(id: Int, targetFg: Fragment, tag: String? = null) {
    val ft = childFragmentManager.beginTransaction()
    if (!targetFg.isAdded) {
        tag?.run {
            ft?.add(id, targetFg, tag)
        } ?: ft?.add(id, targetFg)
    }
    ft.show(targetFg)
    ft.commitAllowingStateLoss()
}


fun Activity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Activity.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}


fun Activity.openActivityAndFinish(pClass: Class<*>) {
    openActivity(pClass, null, true)
}

fun Activity.openActivityAndFinish(pClass: Class<*>, pBundle: Bundle?) {
    openActivity(pClass, pBundle, true)
}

fun Activity.openActivity(pClass: Class<*>, pBundle: Bundle?) {
    openActivity(pClass, pBundle, false)
}

fun Activity.openActivity(pClass: Class<*>) {
    openActivity(pClass, null, false)
}


fun Fragment.openActivityAndFinish(pClass: Class<*>) {
    activity?.openActivity(pClass, null, true)
}

fun Fragment.openActivityAndFinish(pClass: Class<*>, pBundle: Bundle?) {
    activity?.openActivity(pClass, pBundle, true)
}

fun Fragment.openActivity(pClass: Class<*>, pBundle: Bundle?) {
    activity?.openActivity(pClass, pBundle, false)
}

fun Fragment.openActivity(pClass: Class<*>) {
    activity?.openActivity(pClass, null, false)
}

fun Fragment.openActivityForResult(pClass: Class<*>, pBundle: Bundle, requestCode: Int) {
    val intent = Intent(activity, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.openActivityForResult(pClass: Class<*>, requestCode: Int) {
    val intent = Intent(activity, pClass)
    startActivityForResult(intent, requestCode)
}


fun Activity.openActivity(pClass: Class<*>, pBundle: Bundle?, isFinish: Boolean) {
    val intent = Intent(this, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }

    startActivity(intent)
    if (isFinish) {
        finish()
    }
}

fun isNotCollectionEmpty(c: List<*>?): Boolean {
    return c != null && c.size != 0
}

/**
 * 隐藏软键盘
 */
fun Activity.hideInput() {
    val inputMethodManager = getSystemService(Context
            .INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isActive && currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}


//显示虚拟键盘
fun Activity.showKeyboard(v: View) {
    val inputManager = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(v, 0)
}

//EditText文字内容改变监听
fun EditText.setTextChangeListener(body: (key: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            body(s.toString())
        }
    })
}
//宽度充满全屏并且底部弹出
fun AlertDialog.fillWBottom() {
    val window = window
    window.setGravity(Gravity.BOTTOM)
    window.decorView.setPadding(0, 0, 0, 0)
    val lp = window.attributes
    lp.width = window.windowManager.defaultDisplay.width
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    window.attributes = lp
}

//显示dialog
var myDialog: Dialog? = null


fun Activity.dismissLoading() {
    if (myDialog != null)
        myDialog?.dismiss()
}

//选中状态根据

//密码状态
fun passwordStateChange(showPassword: Boolean, vararg ets: EditText) {
    for (et in ets) {
        val selectionStart = et.selectionStart
        if (showPassword) {
            //显示密码
            et.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            //否则隐藏密码
            et.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        et.setSelection(selectionStart)
    }
}

fun getPhoneCodeAndPhoneNum(code: String, phone: String): String {
    return if (code == "0886" && phone.length == 10 && phone.startsWith("0")) {
        "$code${phone.substring(1)}"
    } else {
        "$code$phone"
    }
}

fun getPhoneCodeAndPhoneNum(code: TextView, phone: TextView): String {
   return getPhoneCodeAndPhoneNum(code,phone.text.toString())
}

fun getPhoneCodeAndPhoneNum(code: TextView, phone: String): String {
    val phoneCode = getPhoneCode(code)
    return getPhoneCodeAndPhoneNum(phoneCode,phone)
}


//获取手机前缀
fun getPhoneCode(et: TextView): String {
    var replace = et.text.toString().replace("+", "")
    when {
        replace.length == 3 -> replace = "0$replace"
        replace.length == 2 -> replace = "00$replace"
        replace.length == 1 -> replace = "000$replace"
    }
    return replace
}


fun EditText.getNotNullText(): String {
    return if (text.isNullOrBlank()) {
        "0"
    } else {
        text.toString()
    }
}

