package utils.factory.filebuild

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import utils.CommonUtil
import utils.FreeMarkerUtil
import utils.factory.BaseFile
import utils.factory.MakerFileType
import java.util.*
import kotlin.collections.HashMap

class LayoutFileBuild : BaseFile {
    private lateinit var buildFileName:String//xxx.kt 整个文件名
    private lateinit var buildFilePath:String//文件输出的路径
    private var buildFileType= MakerFileType.FILE_XML//构建模板类型
    private lateinit var actionEv: AnActionEvent//
    private lateinit var targetFile:String//对应的ftl模板文件
    private lateinit var maps:HashMap<String,String>//ftl 插入的
    private lateinit var originName:String//class 名 不带 文件后缀
    private lateinit var targetFileName:String//依赖的activity名


    constructor() : super(){
    }
    override var fileName: String
        get() = buildFileName
        set(value) {
            buildFileName= value
            originName=buildFileName
            if(!buildFileName.isEmpty()){
                buildFileName += ".xml"
            }
        }
    override var actionEvent: AnActionEvent
        get() = actionEv
        set(value) {
            actionEv=value
            val file = LangDataKeys.VIRTUAL_FILE.getData(actionEv.dataContext)
            file?.path?.run {
                buildFilePath=CommonUtil.getLayoutPath(actionEv)
                targetFile="Layout.xml.ftl"
                createMaps()//创建嵌入的key
            }
        }
    override var targetName: String
        get() = targetFileName
        set(value) {targetFileName=value}

    private fun createMaps(){
        var brName=CommonUtil.getUIVMName(targetFileName)
        brName=CommonUtil.toUpperOrNot(brName,false)!!
        val uiViewModelName="${CommonUtil.getChoosePackage(actionEvent)}.ui.viewmodel.${CommonUtil.getUIVMName(targetFileName)}"
        //插入ftl 文件的内容构建
        maps= hashMapOf("UIVIEWMODEL" to brName,
                "UIVIEWMODELPATH" to  uiViewModelName)
    }
    override fun createFile() {
        if(buildFileName.isEmpty()){//允许不创建layout
           return
        }
        FreeMarkerUtil(actionEv).createFiles(buildFileType,buildFileName,buildFilePath,targetFile,maps)
    }
}