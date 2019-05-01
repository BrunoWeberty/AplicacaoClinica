package beans;

import classesBO.ConsultaBO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import objetos.Consulta;

@ManagedBean
@RequestScoped
public class BeanConsulta 
{
    private ConsultaBO cBO;
    private Consulta c;
    
    public BeanConsulta()
    {
        this.setC(new Consulta());
        this.setcBO(new ConsultaBO());
    }
    
    public List <Consulta> getConsultas()
    {
        return cBO.getConsultas();
    }    

    public ConsultaBO getcBO()
    {
        return cBO;
    }

    public void setcBO(ConsultaBO cBO)
    {
        this.cBO = cBO;
    }

    public Consulta getC() 
    {
        return c;
    }

    public void setC(Consulta c) 
    {
        this.c = c;
    }
    
    
}
