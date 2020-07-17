package utils

import com.intellij.ide.IdeView
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import jdk.internal.org.xml.sax.SAXException
import org.w3c.dom.Element
import utils.factory.MakerFileType
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import kotlin.collections.HashMap
/**
 * 模板使用freemarker 请套接
 */
class FreeMarkerUtil {
    private var cfg: Configuration? = null
    private var action: AnActionEvent
    private var basePath:String
    private var userName:String
    private var project:Project

    constructor(actionEvent: AnActionEvent){
        action=actionEvent
        val file = LangDataKeys.VIRTUAL_FILE.getData(action.dataContext)
        basePath = file?.path!!
        val map = System.getenv()
        userName = map["USERNAME"]!! // 获取用户名
        project = action.getData(PlatformDataKeys.PROJECT)!!
        try {
            initTemp("Templates")
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }
    fun initTemp(templateDir: String) {
        // 初始化FreeMarker配置
        cfg = Configuration()
        cfg!!.setClassForTemplateLoading(this.javaClass, "/$templateDir/")
        cfg!!.defaultEncoding = "UTF-8"
        cfg!!.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
    }

    /**
     * 创建文件
     * @param activity 活动
     * @param name     文件名称
     * @param toPath   路径
     */
    @Throws(IOException::class)
    fun createFiles(fileType:MakerFileType, fileName: String, filepath: String,targetFile:String,maps:HashMap<String,String>) {
        try {
            //得到当前路径(相对路径，一是点击右键的位置)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val time = sdf.format(System.currentTimeMillis())
            if(fileType!=MakerFileType.FILE_XML) {
                maps.put("DATE", time)
                maps?.put("USER", userName)
            }
            val template = cfg?.getTemplate(targetFile)
            println("template_test$template")
            FreeMarkerUtil(action).buildTemplate(maps,filepath,fileName,template!!)
        } catch (e: IOException) {
            e.printStackTrace()
            System.err.print(e.message)
        }

    }

    /****
     * 执行写文件操作
     */
    fun buildTemplate(root: Map<*, *>?, filepath: String,
                      fileName: String, template: Template) {
        val floder = File(filepath)
        if (!floder.exists()) {
            val b = floder.mkdir()
            val c=floder.mkdirs()
        } else {
        }
        val file = File("$filepath/$fileName")
        //存在该布局文件就不文件操作
        if (file.exists() && fileName.contains(".xml")) {
            return
        }
        try {
            val out: Writer = OutputStreamWriter(FileOutputStream(
                    file), "UTF-8")
            template.process(root, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
