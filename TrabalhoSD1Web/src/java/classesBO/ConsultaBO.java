package classesBO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import objetos.Consulta;

public class ConsultaBO
{
    AcessoArquivo a = new AcessoArquivo();
    File diretorio = a.criarDiretorio();
    File arquivo = new File(diretorio, "consultas.txt");

    //**Lendo do arquivo
    //ArrayList<String> auxConsultas = new ArrayList<String>();
    
    private String nomeArquivo;
    private String cpfArquivo;
    private String dataArquivo;
    private String horaArquivo;
    
    
    public List<Consulta> getConsultas(){
        List<Consulta> lstC = new LinkedList<>();
        try {
            //Essa classe é usada quando é preciso ler dados de um arquivo
            FileReader fileReader = new FileReader(arquivo);

            //a classe BufferedReader é usada quando se precisa ler dados
            //dados esses que podem ser arquivos, sockets, teclado, etc... (dados externos ao programa)
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //String auxiliar que irá receber cada linha do arquivo
            String linha = "";

            //esse laço percorre o arquivo resgatando cada linha nele escrita, ou seja, cada linha
            //que não seja vazia (null)

                while ((linha = bufferedReader.readLine()) != null) {
                    Consulta c = new Consulta();
                    //Separando os dados que vem do paciente
                    String[] auxLinhas2 = linha.split(";");
                    nomeArquivo = auxLinhas2[0];
                    cpfArquivo = auxLinhas2[1];
                    dataArquivo = auxLinhas2[2]; //pegando data da linha do arquivo
                    horaArquivo = auxLinhas2[3]; //pegando a hora da linha do arquivo

                    c.setNome(nomeArquivo);
                    c.setCpf(cpfArquivo);
                    c.setData(dataArquivo);
                    c.setHorario(horaArquivo);

                    lstC.add(c);

                }
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
    
        return lstC;
    }
}
