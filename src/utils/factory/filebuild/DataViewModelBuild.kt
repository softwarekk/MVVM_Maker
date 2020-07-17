package utils.factory.filebuild

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import utils.CommonUtil
import utils.FreeMarkerUtil
import utils.factory.BaseFile
import utils.factory.MakerFileType

class DataViewModelBuild : BaseFile {
    private lateinit var buildFileName:String//xxx.kt 整个文件名
    private lateinit var buildFilePath:String//.kt文件输出的路径
    private var buildFileType= MakerFileType.FILE_DATA_VM//构建模板类型
    private lateinit var actionEv: AnActionEvent//
    private lateinit var targetFile:String//对应的ftl模板文件
    private lateinit var maps:HashMap<String,String>//ftl 插入的
    private lateinit var originName:String//class 名 不带 kt 文件后缀


    constructor() : super(){
    }

    override var fileName: String
        get() = buildFileName
        set(value) {
            buildFileName=CommonUtil.getDataVMName(value)
            originName=buildFileName
            buildFileName += ".kt"
        }
    override var actionEvent: AnActionEvent
        get() = actionEv
        set(value) {
            actionEv=value
            val file = LangDataKeys.VIRTUAL_FILE.getData(actionEv.dataContext)
            file?.path?.run {
                buildFilePath="$this/data/viewmodel"
                targetFile="DataVM.kt.ftl"
                createMaps()//创建嵌入的key
            }
        }
    private fun createMaps(){
        //插入ftl 文件的内容构建
        maps= hashMapOf("NAME" to originName,
                "PACKAGE_NAME" to  "${CommonUtil.getChoosePackage(actionEvent)}.data.viewmodel")
    }
    override var targetName: String
        get() = "targetFileName"
        set(value) {}
    override fun createFile() {
        FreeMarkerUtil(actionEv).createFiles(buildFileType,buildFileName,buildFilePath,targetFile,maps)
    }
}