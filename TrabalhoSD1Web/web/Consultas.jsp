<%-- 
    Document   : Consultas
    Created on : 24/09/2018, 23:42:12
    Author     : Bruno_TI
--%>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Consultas - Fique Vivo</title>
        </head>
        <body>
            <div class="container">
            <h1><h:outputText  value="Consultas - Fique Vivo"/></h1>
            <h:form>
                <h:dataTable styleClass="table table-striped" var="consultas" value="#{beanConsulta.consultas}">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Nome" />
                        </f:facet>
                        <h:outputText value="#{consultas.nome}" />
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="CPF" />
                        </f:facet>
                        <h:outputText value="#{consultas.cpf}" />
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Data" />
                        </f:facet>
                        <h:outputText value="#{consultas.data}" />
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Horário" />
                        </f:facet>
                        <h:outputText value="#{consultas.horario}" />
                    </h:column>
                </h:dataTable>
            </h:form>
            </div>
        </body>
    </html>
</f:view>
    
