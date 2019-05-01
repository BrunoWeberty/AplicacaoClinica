package trabalhosd1servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;
import arquivos.AcessoArquivo;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Emerson Gabriel
 */
public class TrabalhoSD1Servidor extends Thread {

    public static void main(String[] args) {
        // instancia o vetor de clientes conectados
        pacientes = new Vector();
        try {
            // criando um socket que fica escutando a porta 2222.
            ServerSocket s = new ServerSocket(2222);
            // Loop principal.
            while (true) {
                System.out.println("Esperando paciente acessar o sistema...");
                Socket conexao = s.accept();
                System.out.println("Paciente conectado!");
                // cria uma nova thread para tratar essa conexão
                Thread t = new TrabalhoSD1Servidor(conexao);
                t.start();
                // voltando ao loop, esperando mais alguém se conectar.
            }
        } catch (IOException e) {
            // caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }
    // Note que a instanciação está no main.
    private static Vector pacientes;
    // socket deste cliente
    private Socket conexao;
    // nome deste cliente
    private String dados;
    // construtor que recebe o socket deste cliente

    private String dataArquivo;
    private String horaArquivo;
    private String dataUsuario;
    private String horaUsuario;

    int verifica = 0;

    public TrabalhoSD1Servidor(Socket s) {
        conexao = s;
    }
    // execução da thread

    public void run() {
        try {
            // objetos que permitem controlar fluxo de comunicação
            //ler algum dado de algum local (uma fonte) 
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            //precise escrever um dado em algum local (destino)
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            // primeiramente, espera-se pelo nome do cliente
            dados = entrada.readLine();

            if (dados == null) {
                return;
            } else {
                AcessoArquivo a = new AcessoArquivo();
                File diretorio = a.criarDiretorio();
                File arquivo = new File(diretorio, "consultas.txt");

                //**Lendo do arquivo
                ArrayList<String> auxConsultas = new ArrayList<String>();

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
                        //Aqui a linha é impressa no prompt
                        //System.out.println(linha);
                        //separar a data e a hora
                        auxConsultas.add(linha);

                        //Separando os dados que vem do paciente
                        String[] auxLinhas2 = linha.split(";");
                        dataArquivo = auxLinhas2[2]; //pegando data da linha do arquivo
                        horaArquivo = auxLinhas2[3]; //pegando a hora da linha do arquivo

//                        System.out.println("Data de uma consulta é: "+dataArquivo+", e a hora desta consulta é: " + horaArquivo);
                        //pegando os dados do cliente que acabou de enviar
                        String[] auxLinhas = dados.split(";");
                        dataUsuario = auxLinhas[2];
                        horaUsuario = auxLinhas[3];

                        if ((dataArquivo.equals(dataUsuario)) && (horaArquivo.equals(horaUsuario))) { // tratando a data igual se for igual é 0
                            verifica += 1;
                            break;
                        }

                    }

                    if (verifica == 0) {// se entrar aqui ja pode cadastrar
                        //***Escrevendo no arquivo
                        try {
                            //o metodo abaixo efetivamente cria o arquivo dentro do diretório passado no objeto File
                            //da mesma forma, se ele não existir é criado, senão nada acontece
                            boolean statusArq = arquivo.createNewFile();

                            FileWriter fileWriter = new FileWriter(arquivo, true);
                            //essa classe é que efetivamente envia os dados para o arquivo
                            PrintWriter printWriter = new PrintWriter(fileWriter);

                            printWriter.print(dados);
                            printWriter.println();
                            //O flush indica que os dados a serem inseridos ja estão preparados para serem efetivamente 
                            //inseridos no arquivo
                            printWriter.flush();
                            //fecha a conexão com o arquivo
                            printWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sendToA(saida, "Consulta salva com sucesso.");
                        sendToA(saida, "Aguarde o contato telefônico, para confirmação da solicitação da sua consulta.");
//                        System.out.println("Consulta salva com sucesso.");
//                        System.out.println("Aguarde o contato para realizar a confirmação.");
                    } else {// se entrar aqui ja existe consulta nessa mesma data e horario
                        sendToA(saida, "Infelizmente não foi possivel cadastrar sua consulta nesta data e horário.");
                        sendToA(saida, "Realize o cadastro novamente informando um horário ou data diferente.");
//                        System.out.println("Infelizmente não foi possivel, salvar sua consulta nesta data.");
//                        System.out.println("Tente novamente em outra data.");
                    }

                    //Separando os dados que vem do paciente
//                    String[] auxLinhas = dados.split(";");
//                for(String aux : auxLinhas)//For que roda objetos, ArrayList em que cada posição dele tem um tipo string que cria
//                //um aux para imprimir as posições dentro do objeto
//                {
//--------------------------------------------------------------
//                        aqui eu tenho a data e a hora que o servidor acabou de receber
//                    System.out.println("informacao data que enviou agora: "+auxLinhas[2]);
//                    System.out.println("informacao hora que enviou agr"+auxLinhas[3]);
//                }
                    //libera-se o fluxo dos objetos
                    // e fecha-se o arquivo
                    fileReader.close();
                    bufferedReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            pacientes.add(saida);

            String linha = entrada.readLine();
            while (linha != null && !(linha.trim().equals(""))) {

                sendToA(saida, " disse: ");

                linha = entrada.readLine();
            }

            //sendToA(saida, " saiu ");
            pacientes.remove(saida);
            conexao.close();
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
    }
    // enviar uma mensagem para todos, menos para o próprio

    public void sendToA(PrintStream saida, String linha) throws IOException {

        
        if ((linha != null) && !(linha.trim().equals("")) ){
            saida.println("Resposta do sistema: " + linha);
        }
    }
}
