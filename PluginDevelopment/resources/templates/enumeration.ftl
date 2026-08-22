package ${enumeration.typePackage};

public enum ${enumeration.name} {

<#list 0..enumeration.valuesCount-1 as i>
    ${enumeration.getValueAt(i)}<#if i < enumeration.valuesCount-1>,</#if>
</#list>

    // <protected region name="custom">

    // </protected region>
}