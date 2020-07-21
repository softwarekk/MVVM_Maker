package ${PACKAGE_NAME}

import com.young.aac.base.MvvmBaseActivity
import ${PACKAGEBINDINGNAME}
import ${UIVIEWMODELPATH}
import ${DATAVIEWMODELPATH}
import ${BASECHOOSEPACKAGE}.BR
import ${BASECHOOSEPACKAGE}.R
/**
 * Des
 * Created by ${USER} on ${DATE}
 */
class ${NAME} :  MvvmBaseActivity<${BINDING}, ${UIVIEWMODEL},${DATAVIEWMODEL}>(){


    override fun getUIViewModel(): ${UIVIEWMODEL} {
        return getActivityViewModelProvider(this).get(${UIVIEWMODEL}::class.java)
    }
    override fun getDataViewModel(): ${DATAVIEWMODEL}? {
            return getActivityViewModelProvider(this).get(${DATAVIEWMODEL}::class.java)!!
    }
    override val bindingVariable: Int
        get() = BR.${UIVIEWMODELLOWER}
    override val layoutId: Int
        get() = R.layout.${LAYOUTRESOURCE}
}