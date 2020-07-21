package view

import utils.JTextFieldHintListener
import java.awt.*
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.*

/*
* 怎么看怎么low的UI
* 玩不转  SOS
* */
class LowStyleDialog :JFrame {
    constructor(title: String?) : super(title){
        initPanel()
        setDialog()
        setConstraint()
        changeImg()
    }

    // GridBagLayout不要求组件的大小相同便可以将组件垂直、水平或沿它们的基线对齐
    private val mLayout = GridBagLayout()
    // GridBagConstraints用来控制添加进的组件的显示位置
    private val mConstraints = GridBagConstraints()
    private val mContentJPanel = JPanel()
    private val lineOnePanel = JPanel()
    private val lineTwoPanel = JPanel()

    private val xmlText = JTextField()
    private val xmlLeftText=JLabel()

    private val inputText = JTextField()
    private val inputLeftText=JLabel()

    private val mPanelText= JPanel()
    private val mButtonConfirm = JButton("确定")
    private val mButtonCancel = JButton("取消")
    private fun initPanel() {
        mContentJPanel.add(mButtonConfirm)
        mContentJPanel.add(mButtonCancel)
        mContentJPanel.setSize(700, 50)
        //字体
        val font = Font("Microsoft Uighur", Font.ITALIC, 30)
        val font2 = Font("Microsoft Uighur", Font.ITALIC, 25)

        mButtonConfirm.font=font2
        mButtonCancel.font=font2
        inputText.font=font
        inputLeftText.text="Act/FM:"
        inputLeftText.font=font
        inputLeftText.horizontalAlignment=JLabel.CENTER
        inputLeftText.setPreferredSize(Dimension(150, 50))
        inputText.addFocusListener(JTextFieldHintListener(inputText,"call me activity or fragment"))
        inputText.horizontalAlignment=JTextField.LEFT
        inputText.setPreferredSize(Dimension(350, 50))
        inputText.setSize(350,50)
        lineOnePanel.add(inputLeftText,0)
        lineOnePanel.add(inputText,1)

        xmlText.font=font
        xmlLeftText.text="xml:"
        xmlLeftText.font=font
        xmlText.addFocusListener(JTextFieldHintListener(xmlText,"call me style of xxx_xxx "))
        xmlLeftText.horizontalAlignment=JLabel.CENTER
        xmlLeftText.setPreferredSize(Dimension(150, 50))
        xmlText.horizontalAlignment=JTextField.LEFT
        xmlText.setPreferredSize(Dimension(350, 50))
        xmlText.setSize(350,50)

        lineTwoPanel.add(xmlLeftText)
        lineTwoPanel.add(xmlText)


        mPanelText.add(lineOnePanel,0)
        mPanelText.add(lineTwoPanel,1)
        mPanelText.setBounds(100, 100, 0,0)
        mPanelText.setPreferredSize(Dimension(600, 140))
        mPanelText.setSize(700, 100)
        // 添加到JFrame
        contentPane.add(mPanelText, 0)
        contentPane.add(mContentJPanel, 1)
        contentPane.setSize(600,150)
    }
    fun setListener(listen:ActionListener){
        mButtonConfirm.addActionListener(listen)
        enterPressesWhenFocused(mButtonConfirm,listen)
        mButtonCancel.addActionListener {
            setGone()
        }
    }
    fun enterPressesWhenFocused(okBtn: JButton,
                                actionListener: ActionListener?) {
        okBtn.registerKeyboardAction(actionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_IN_FOCUSED_WINDOW)
        okBtn.registerKeyboardAction(actionListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_IN_FOCUSED_WINDOW)
    }
    fun setGone(){
        isVisible = false
        dispose()
    }
    private fun setDialog() {
        // 不可拉伸
        isResizable = true
        // 设置大小
        setSize(1500, 650)
        // 设置居中，放在setSize后面
        setLocationRelativeTo(null)
        // 显示最前
        isAlwaysOnTop = true
    }
    fun setConstraint(){
        mConstraints.fill = GridBagConstraints.BOTH
        mConstraints.gridwidth = 1
        mConstraints.gridx = 0
        mConstraints.gridy = 1
        mConstraints.weightx = 1.0
        mConstraints.weighty = 1.0
        mLayout.setConstraints(mPanelText, mConstraints)
        mConstraints.fill = GridBagConstraints.NONE
        mConstraints.gridwidth = 0
        mConstraints.gridx = 0
        mConstraints.gridy = 3
        mConstraints.weightx = 0.0
        mConstraints.weighty = 0.0
        mConstraints.anchor = GridBagConstraints.EAST
        mLayout.setConstraints(mContentJPanel, mConstraints)
        mConstraints.gridwidth = 1
        mConstraints.gridheight = 1
        mConstraints.gridy = 1
        mConstraints.gridx = 1
        mPanelText.add(lineOnePanel, mConstraints)
        mConstraints.gridy = 2;
        mPanelText.add(lineTwoPanel, mConstraints);

        layout=mLayout
    }
    fun changeImg(){
        val tk = Toolkit.getDefaultToolkit()
        //根据路径获取图片
        val i = tk.getImage("")
        //给窗体设置图片
        iconImage = i
    }

    fun getFileName() : String{
       return inputText?.text
    }
    fun getXmlName():String{
        return xmlText?.text
    }
    fun  modifyComponentSize( frame:JFrame, proportionW:Float, proportionH:Float){

        try
        {
            val components = frame.getRootPane().getContentPane().getComponents();
            for( co in components)
            {
                val locX = co.getX() * proportionW
                val locY = co.getY() * proportionH
                val width = co.getWidth() * proportionW
                val height = co.getHeight() * proportionH
                co.setLocation((locX.toInt()), locY.toInt())
                co.setSize(width.toInt(), height.toInt())
                val size = (co.getFont().getSize() * proportionH)
                val font =  Font(co.font.fontName, co.font.style, size.toInt())
                co.setFont(font);
            }
        }
        catch (e:Exception)
        {
            // TODO: handle exception
        }
    }
}