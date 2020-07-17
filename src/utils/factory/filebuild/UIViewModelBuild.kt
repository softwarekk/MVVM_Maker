package utils.factory.filebuild

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import utils.CommonUtil
import utils.FreeMarkerUtil
import utils.factory.BaseFile
import utils.factory.MakerFileType

/*
* 构建UIviewModel
* */
class UIViewModelBuild: BaseFile {
    private lateinit var buildFileName:String//xxx.kt 整个文件名
    private lateinit var buildFilePath:String//.kt文件输出的路径
    private var buildFileType= MakerFileType.FILE_UI_VM//构建模板类型
    private lateinit var actionEv: AnActionEvent//
    private lateinit var targetFile:String//对应的ftl模板文件
    private lateinit var maps:HashMap<String,String>//ftl 插入的
    private lateinit var originName:String//class 名 不带 kt 文件后缀


    constructor() : super(){
    }

    override var fileName: String
        get() = buildFileName
        set(value) {
            buildFileName=CommonUtil.getUIVMName(value)
            originName=buildFileName
            buildFileName += ".kt"
        }
    override var actionEvent: AnActionEvent
        get() = actionEv
        set(value) {
            actionEv=value
            val file = LangDataKeys.VIRTUAL_FILE.getData(actionEv.dataContext)
            file?.path?.run {
                buildFilePath="$this/ui/viewmodel"
                targetFile="UIVM.kt.ftl"
                createMaps()//创建嵌入的key
            }
        }
    override var targetName: String
        get() = "targetFileName"
        set(value) {}
    private fun createMaps(){
        //得到当前路径(相对路径，一是点击右键的位置)
        maps= hashMapOf("NAME" to originName,
                "PACKAGE_NAME" to  "${CommonUtil.getChoosePackage(actionEvent)}.ui.viewmodel")
    }
    override fun createFile() {
        FreeMarkerUtil(actionEv).createFiles(buildFileType,buildFileName,buildFilePath,targetFile,maps)
    }
}