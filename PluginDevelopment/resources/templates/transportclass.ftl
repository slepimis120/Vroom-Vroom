package ${class.typePackage};

import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ${class.name} {

<#list properties as property>

<#if property.name == "id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
</#if>

<#if property.association>

    <#if property.upper == -1>
    @OneToMany
    private List<${property.type}> ${property.name};

    <#elseif property.oppositeUpper == -1>
    @ManyToOne
    private ${property.type} ${property.name};

    <#else>
    @OneToOne
    private ${property.type} ${property.name};
    </#if>

<#else>
    private ${property.type} ${property.name};
</#if>

</#list>


<#list properties as property>

<#if property.association && property.upper == -1>

    public List<${property.type}> get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(List<${property.type}> ${property.name}) {
        this.${property.name} = ${property.name};
    }

<#else>

    public ${property.type} get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(${property.type} ${property.name}) {
        this.${property.name} = ${property.name};
    }

</#if>

</#list>

}