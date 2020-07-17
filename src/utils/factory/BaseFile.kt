package utils.factory

import com.intellij.openapi.actionSystem.AnActionEvent

open  abstract class BaseFile {
    abstract var fileName:String
    abstract var actionEvent: AnActionEvent
    abstract var targetName:String//关联的关系文件 activity -》xml xml -》UIViewmode
    abstract fun createFile()
}