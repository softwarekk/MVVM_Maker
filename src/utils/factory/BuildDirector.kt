package utils.factory

class BuildDirector {

    lateinit var baseFile: BaseFile
    fun build(file:BaseFile) : BuildDirector {
        baseFile=file
        return this
    }
    fun createFile():BuildDirector{
        baseFile.createFile()
        return this
    }
}