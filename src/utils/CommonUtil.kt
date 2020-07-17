package utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys

object CommonUtil {


    fun getUIVMName(value:String) :String{
        var buildFileName =""
        if(value.endsWith("Activity")){
            buildFileName="${value.substringBeforeLast("Activity")}UIVM"
        }else if(value.endsWith("Fragment")){
            buildFileName="${value.substringBeforeLast("Fragment")}UIVM"
        }else{
            buildFileName="${value}UIVM"
        }
        return buildFileName
    }

    fun getDataVMName(value:String):String{
        var buildFileName =""
        if(value.endsWith("Activity")){
            buildFileName="${value.substringBeforeLast("Activity")}DataVM"
        }else if(value.endsWith("Fragment")){
            buildFileName="${value.substringBeforeLast("Fragment")}DataVM"
        }else{
            buildFileName="${value}DataVM"
        }
        return buildFileName
    }

    fun getLayoutPath(actionEvent: AnActionEvent):String{
        val file = LangDataKeys.VIRTUAL_FILE.getData(actionEvent.dataContext)
        var layoutPath=""
        var mainPath=file.toString().substringBefore("java/")
        mainPath=mainPath.substringAfter("file:/")
        layoutPath=mainPath+"res/layout"
        return layoutPath
    }
    fun getChoosePackage(actionEvent: AnActionEvent):String{
        val file = LangDataKeys.VIRTUAL_FILE.getData(actionEvent.dataContext)
        var packagePath=""//com.xx.xx
        val packageSub=file.toString().substringAfterLast("java/")
        packagePath=packageSub.replace("/",".")
        return packagePath
    }
    fun getBindingName(xmlName:String):String{
        //根据xx_xx_layout 获取binding名
        if(xmlName.isEmpty() ){
            return ""
        }
        var xmls=xmlName.split("_")
        var bindingName=""
        for (xml in xmls) {
            bindingName+= toUpperOrNot(xml,true)
        }
        bindingName+="Binding"
        return bindingName
    }
    fun toUpperOrNot(s: String, b: Boolean): String? {
        val cs = s.toCharArray()
        val c = cs[0]
        var isSmall = false
        if (c >= 'a' && c <= 'z') {
            isSmall = true
        }
        if (b) {
            //转换为大写
            if (isSmall) {
                cs.set(0,c.toUpperCase())
            }
        } else {
            if (!isSmall) {
                cs.set(0,c.toLowerCase())
            }
        }
        return String(cs)
    }
    fun isEmpty(input: String?): Boolean {
        if (input == null || "" == input) return true
        for (element in input) {
            val c = element
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false
            }
        }
        return true
    }
}
