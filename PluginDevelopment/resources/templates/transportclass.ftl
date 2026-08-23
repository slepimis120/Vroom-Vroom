package ${class.typePackage};

import java.util.List;
<#if properties?filter(p -> p.type == "Date")?size gt 0>
import java.util.Date;
</#if>

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = ${class.name}.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ${class.name} {

<#list properties as property>
<#if property.association>
<#if property.upper == -1>
    @OneToMany(mappedBy = "${class.name?uncap_first}", fetch = FetchType.${property.fetch})
    private List<${property.type}> ${property.name};
<#elseif property.oppositeUpper == -1>
    @ManyToOne(fetch = FetchType.${property.fetch})
    @JoinColumn(name = "${property.name}_id")
    private ${property.type} ${property.name};
<#else>
    @OneToOne(fetch = FetchType.${property.fetch})
    private ${property.type} ${property.name};
</#if>
<#elseif property.id>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ${property.name};
<#else>
<#if property.label??>    /** ${property.label} */
</#if><#if property.type == "TransportType">    @Enumerated(EnumType.STRING)
</#if>    @Column(<#if property.columnName??>name = "${property.columnName}", </#if>nullable = <#if property.required>false<#else>true</#if><#if property.unique>, unique = true</#if><#if property.type == "String">, length = ${property.length}</#if>)
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
    public <#if property.id>Long<#else>${property.type}</#if> get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(<#if property.id>Long<#else>${property.type}</#if> ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#if>

</#list>

    // <protected region name="custom">

    // </protected region>

}