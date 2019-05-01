package pacientes;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;

/**
 * @author Emerson Gabriel
 */
public class Paciente extends Thread {

    // Flag que indica quando se deve terminar a execução.
    private static boolean done = false;

    public static void main(String[] args) {

        try {
            Socket conexao = new Socket("127.0.0.1", 2222);
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            // enviar antes de tudo o nome do usuário
            BufferedReader teclado
                    = new BufferedReader(new InputStreamReader(System.in));
            String dados = "";
            System.out.println("Seja bem ao sistema de pré-agendamento da clínica [- Fique Vivo -]: ");
            System.out.print("Entre com o seu nome: ");
            String nomePaciente = teclado.readLine();
            dados += nomePaciente + ";";
            // ler a linha digitada no teclado
            System.out.print("Digite um CPF válido: ");
            String CPFPaciente = teclado.readLine();
            dados += CPFPaciente + ";";
//        saida.println(CPFPaciente);
            System.out.print("Digite a data para sua consulta no formato ex.:[13/01/2018]: ");
            String data = teclado.readLine();
            dados += data + ";";
//        saida.println(data);
            System.out.print("Digite o horário da sua consulta ex.:[16:30]: ");
            String hora = teclado.readLine();
            dados += hora + ";";
//        saida.println(hora);
            System.out.println("*---------------------------------------------*");
            System.out.println("*Aguarde sua consulta está sendo processada...*");
            System.out.println("*---------------------------------------------*");
            ///SLEEP para esperar 5 segundos
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            };
            saida.println(dados);//passa nome pro servisor

//        saida.println(nomePaciente);
            Thread t = new Paciente(conexao);
            t.start();

            String linha;

            while (true) {
                // ler a linha digitada no teclado
                System.out.print("");
                linha = teclado.readLine();
                // antes de enviar, verifica se a conexão não foi fechada
                if (done) {
                    break;
                }
                // envia para o servidor
                saida.println(linha);

            }
        } catch (IOException e) {
            // Caso ocorra alguma excessão de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
        System.out.println("A Fique Vivo, agradece pela preferência. Volte sempre!!!");
    }

    // parte que controla a recepção de mensagens deste cliente
    private Socket conexao;
    // construtor que recebe o socket deste cliente

    public Paciente(Socket s) {
        conexao = s;
    }
    // execução da thread

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while (true) {
                // pega o que o servidor enviou
                linha = entrada.readLine();
                // verifica se é uma linha válida. Pode ser que a conexão
                // foi interrompida. Neste caso, a linha é null. Se isso
                // ocorrer, termina-se a execução saindo com break
                if (linha == null) {
                    System.out.println("Solicitação de consulta encerrada!");
                    break;
                }
                // caso a linha não seja nula, deve-se imprimi-la
                System.out.println(linha);
                
            }
        } catch (IOException e) {
            // caso ocorra alguma exceção de E/S, mostre qual foi.
            System.out.println("IOException: " + e);
        }
        // sinaliza para o main que a conexão encerrou.
        done = true;
    }

}
