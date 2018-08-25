package com.ifast.tags.processor;

import com.ifast.common.service.DictService;
import com.ifast.tags.util.IftgUtil;
import com.ifast.tags.vo.ValueVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * select 注解
 * 用法
 * <iftg:select dicType = "dic_of_sex"></iftg:select>
 * 属性1 dicType 指定数据字典key
 * --情况1：当dicType = 字典中的type 时select下拉数值渲染的value
 * 为字典中的name字段，name 为字典中的name字段
 * <p>
 * --情况2：当dicType = all时候表示select下拉数值渲染的value
 * 为字典中的type字段，name 为字典中的description字段）
 * <p>
 * --情况3：当optionValue 属性不为空是，则dicType 属性失效
 * 下拉option数据为当optionValue属性的值
 * <p>
 * <p>
 * 属性2 optionValue 控件下拉数据对象
 * --情况1：当optionValue 属性不为空是，则dicType 属性失效
 * 下拉option数据为当optionValue属性的值
 * 注：optionValue 对象的数值必须为 List<ValueVo> 类型
 * <p>
 * 属性3 dataValue 控件回显示数据对象
 * 注：dataValue 对象的数值必须为 List<String> 类型
 * <p>
 *
 * 属性3 defaultValue 控件默认显示数据对象
 *  注：defaultValue 对象的数值必须为 List<String> 类型
 *
 *
 * 注：
 * 控件的其他属性，用户可根据需求完全自定义，如需要加上name属性和Id属性则
 * <iftg:select dicType = "dic_of_sex" name="mySelect" id="selectId"></iftg:select>
 *
 * @author: zet
 * @date:2018/8/22
 */
public class IftgSelectProcessor extends AbstractMarkupSubstitutionElementProcessor {
    private DictService dictService;
    private boolean firstLoad = true;

    public IftgSelectProcessor() {
        super("select");
    }

    /**
     * 核心处理
     *
     * @param arguments thymeleaf 上下文对象
     * @param element   当前节点对象
     * @return
     */
    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        //初始化
        init(arguments);

        //获取值
        String dicType = element.getAttributeValue("dicType");//字典类型
        List<ValueVo> optionValues = IftgUtil.getOptionValues(arguments, element, "optionValue");//控件值
        List<String> dataValues = IftgUtil.getDataValues(arguments, element, "dataValue");//回显值
        List<String> defaultValues = IftgUtil.getDataValues(arguments, element, "defaultValue");//默认选中
        List<String> selectedValues = new ArrayList<>();
        List<ValueVo> valueVos = new ArrayList<>();

        if (Objects.nonNull(dataValues)) {
            selectedValues = dataValues;
        } else {
            selectedValues = defaultValues;
        }

        if (Objects.nonNull(optionValues)) {
            valueVos = optionValues;
        } else {
            valueVos = IftgUtil.getValues(dictService, dicType, selectedValues);
        }

        //set 值
        List<Node> nodes = null;

        //创建对象
        Element selectEle = createSelect(element.getAttributeMap(), valueVos);
        nodes = new ArrayList<>();
        nodes.add(selectEle);

        return nodes;
    }

    /**
     * 初始化
     *
     * @param argument
     */
    private void init(Arguments argument) {
        if (firstLoad) {
            ApplicationContext appCtx = ((SpringWebContext) argument.getContext()).getApplicationContext();
            dictService = appCtx.getBean(DictService.class);
            firstLoad = false;
        }
    }

    /**
     * 创建select对象
     *
     * @param attributeMap select 属性值
     * @param options      下拉值
     * @return
     */
    private Element createSelect(Map<String, Attribute> attributeMap, List<ValueVo> options) {
        Element selectEle = new Element("select");
        Element optionEle = new Element("option");
        optionEle.setAttribute("style", "display: none");
        selectEle.addChild(optionEle);

        //创建option
        for (ValueVo option : options) {
            optionEle = new Element("option");
            optionEle.setAttribute("value", option.getVlaue());

            //默认选中
            if (Objects.nonNull(option.getSelected())
                    && option.getSelected()) {
                optionEle.setAttribute("selected", "selected");
            }

            optionEle.addChild(new Text(option.getName()));
            selectEle.addChild(optionEle);
        }


        //创建属性
        if (Objects.nonNull(attributeMap)) {
            for (String mapKey : attributeMap.keySet()) {
                String key = mapKey;
                String value = attributeMap.get(key).getValue();
                selectEle.setAttribute(key, value);
            }
        }

        return selectEle;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
