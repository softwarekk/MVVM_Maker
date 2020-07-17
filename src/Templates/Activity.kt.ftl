package ${PACKAGE_NAME}

import android.arch.lifecycle.ViewModelProviders
import com.young.businessmvvm.base.MvvmBaseActivity
import ${PACKAGEBINDINGNAME}
import ${UIVIEWMODELPATH}
import ${BASECHOOSEPACKAGE}.BR
import ${BASECHOOSEPACKAGE}.R
/**
 * Des
 * Created by ${USER} on ${DATE}
 */
class ${NAME} :  MvvmBaseActivity<${BINDING}, ${UIVIEWMODEL}>(){
    override fun getViewModel(): ${UIVIEWMODEL} {
        return ViewModelProviders.of(this)[${UIVIEWMODEL}::class.java]
    }
    override val bindingVariable: Int
        get() = BR.${UIVIEWMODELLOWER}
    override val layoutId: Int
        get() = R.layout.${LAYOUTRESOURCE}
    override val titleName: String
        get() = ""

}