package action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import utils.factory.BuildDirector
import utils.factory.filebuild.ActivityOrFragmentFileBuild
import utils.factory.filebuild.DataViewModelBuild
import utils.factory.filebuild.LayoutFileBuild
import utils.factory.filebuild.UIViewModelBuild
import view.LowStyleDialog
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class AACMakerAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog= LowStyleDialog("MVVM文件生成")
        dialog.pack()
        dialog.isVisible = true
        dialog.setListener(object :ActionListener{
            override fun actionPerformed(AE: ActionEvent?) {
                Thread(Runnable {
                    dialog.getFileName()?.run {
                        if(this == "call me activity or fragment"||this==""){
                            Messages.showInfoMessage("call me what???", "Warning")
                            return@Runnable
                        }
                        buildFile(e,this,dialog.getXmlName())
                        dialog.setGone()
                    }
                }).run()
            }
        })
    }
    /*
    * 构建文件
    * xmlName 可以不构建layout
    * */
    private fun buildFile(e: AnActionEvent,fileName:String,xmlName:String){
        var xmlLayout=xmlName
        if(xmlName=="call me style of xxx_xxx ") {
            xmlLayout = ""
        }
        //注意build对象里有成员依赖 最后设置actionEvent
        //activity
        val activityOrFragmentFile= ActivityOrFragmentFileBuild()
        activityOrFragmentFile.fileName=fileName
        activityOrFragmentFile.targetName=xmlLayout//activity 依赖 为xml
        activityOrFragmentFile.actionEvent=e

        //UIVM
        val uiViewModelFile= UIViewModelBuild()
        uiViewModelFile.fileName=fileName
        uiViewModelFile.actionEvent=e

        //DataVM
        val dataVMBuild= DataViewModelBuild()
        dataVMBuild.fileName=fileName
        dataVMBuild.actionEvent=e

//        //xml文件
        val xmlBuild = LayoutFileBuild()
        xmlBuild.fileName = xmlLayout
        xmlBuild.targetName = fileName//xml 依赖的为activity
        xmlBuild.actionEvent = e
        BuildDirector()
                .build(dataVMBuild)
                .createFile()
                .build(activityOrFragmentFile)
                .createFile()
                .build(uiViewModelFile)
                .createFile()
                .build(xmlBuild)
                .createFile()
        e.project?.baseDir?.refresh(false, true)
    }
    companion object{//testing
    @JvmStatic
    fun main(args: Array<String>) {

        val dialog= LowStyleDialog("MVVM构造生成")
        dialog.pack()
        dialog.isVisible = true
        dialog.setListener(object : ActionListener {
            override fun actionPerformed(AE: ActionEvent?) {
                println("__"+ dialog.getFileName())
                Thread(Runnable {
                    dialog.getFileName()?.run {
                        dialog.setGone()
                    }
                }).run()
            }
        })
    }
    }
}