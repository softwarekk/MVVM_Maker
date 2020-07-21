package ${PACKAGE_NAME}

import com.young.aac.base.MvvmBaseFragment
import ${PACKAGEBINDINGNAME}
import ${UIVIEWMODELPATH}
import ${DATAVIEWMODELPATH}
import ${BASECHOOSEPACKAGE}.BR
import ${BASECHOOSEPACKAGE}.R
/**
 * Des
 * Created by ${USER} on ${DATE}
 */
class ${NAME} :  MvvmBaseFragment<${BINDING}, ${UIVIEWMODEL},${DATAVIEWMODEL}>(){


      override fun getUIViewModel(): ${UIVIEWMODEL} {
           return getActivityViewModelProvider(requireActivity()).get(${UIVIEWMODEL}::class.java)
      }
      override fun getDataViewModel(): ${DATAVIEWMODEL}? {
           return getActivityViewModelProvider(requireActivity()).get(${DATAVIEWMODEL}::class.java)!!
      }
      override val bindingVariable: Int
      get() = BR.${UIVIEWMODELLOWER}
      override val layoutId: Int
      get() = R.layout.${LAYOUTRESOURCE}
}