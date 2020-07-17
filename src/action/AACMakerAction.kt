package action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import view.LowStyleDialog
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class AACMakerAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
    }

    companion object{//testing
    @JvmStatic
    fun main(args: Array<String>) {

        val dialog= LowStyleDialog("MVVM文件生成")
        dialog.pack()
        dialog.isVisible = true
        dialog.setListener(object : ActionListener {
            override fun actionPerformed(AE: ActionEvent?) {
                println("__"+ dialog.getFileName())
                Thread(Runnable {
                    dialog.getFileName()?.run {
                        dialog.setGone()
                        println("${dialog.getFileName()}___${dialog.getXmlName()}")
                    }
                }).run()
            }
        })

    }
    }
}