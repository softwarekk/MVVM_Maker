package utils.factory.filebuild

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import utils.CommonUtil
import utils.FreeMarkerUtil
import utils.factory.BaseFile
import utils.factory.MakerFileType

class ActivityOrFragmentFileBuild : BaseFile {
    private lateinit var buildFileName:String//文件名带后缀
    private lateinit var buildFilePath:String//创建文件路径
    private var buildFileType= MakerFileType.FILE_ACTIVITY
    private lateinit var actionEv: AnActionEvent
    private lateinit var buildTargetFile:String//依赖的layout
    private lateinit var maps:HashMap<String,String>//拼接参数
    private lateinit var originName:String//不带后缀
    private lateinit var targetFileName:String//依赖的layout 名

    constructor() : super()

    override var fileName: String
        get() = buildFileName
        set(value) {
            if(value.endsWith("Activity")){
                buildFileName=value
            }else if(value.endsWith("Fragment")){
                buildFileName=value
                buildFileType=MakerFileType.FILE_FRAGMENT
            }else{
                buildFileName="${value}Activity"
            }
            originName=buildFileName
            buildFileName += ".kt"
        }
    override var actionEvent: AnActionEvent
        get() = actionEv
        set(value) {
            actionEv=value
            val file = LangDataKeys.VIRTUAL_FILE.getData(actionEv.dataContext)
            file?.path?.run {
                if(buildFileType==MakerFileType.FILE_ACTIVITY) {
                    buildFilePath = "$this/ui/activity"
                    buildTargetFile = "Activity.kt.ftl"
                }else{
                    buildFilePath = "$this/ui/fragment"
                    buildTargetFile = "Fragment.kt.ftl"
                }
                createMaps()//创建嵌入的key
            }
        }
    override var targetName: String
        get() = targetFileName
        set(value) {
            targetFileName=value
        }


    private fun createMaps(){
        val packageName=
                if(buildFileType==MakerFileType.FILE_ACTIVITY) "${CommonUtil.getChoosePackage(actionEvent)}.ui.activity"
                else "${CommonUtil.getChoosePackage(actionEvent)}.ui.fragment"
        val bindingName=CommonUtil.getBindingName(targetFileName)
        var uiVMName=CommonUtil.getUIVMName(originName)
        val uiVMNameLower=CommonUtil.toUpperOrNot(uiVMName,false)!!
        val dataVMName=CommonUtil.getDataVMName(originName)
        val layoutSource=targetFileName
        val packageBinding=CommonUtil.getChoosePackage(actionEvent)+".databinding."+CommonUtil.getBindingName(targetFileName)
        val uiViewModelPath="${CommonUtil.getChoosePackage(actionEvent)}.ui.viewmodel.$uiVMName"
        val dataViewModelPath="${CommonUtil.getChoosePackage(actionEvent)}.data.viewmodel.$dataVMName"
        maps= hashMapOf("NAME" to originName,
                "BINDING" to bindingName,
                "UIVIEWMODEL" to uiVMName,
                "DATAVIEWMODEL" to dataVMName,
                "DATAVIEWMODELPATH" to dataViewModelPath,
                "UIVIEWMODELLOWER" to uiVMNameLower,
                "PACKAGE_NAME" to  packageName,
                "LAYOUTRESOURCE" to layoutSource,
                "UIVIEWMODELPATH" to uiViewModelPath,
                "PACKAGEBINDINGNAME" to packageBinding,
                "BASECHOOSEPACKAGE" to CommonUtil.getChoosePackage(actionEvent)
        )
    }
    override fun createFile() {
        FreeMarkerUtil(actionEv).createFiles(buildFileType,buildFileName,buildFilePath,buildTargetFile,maps)
    }
}