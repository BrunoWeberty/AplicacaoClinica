/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classesBO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emerson
 */
public class AcessoArquivo {

    public File criarDiretorio() {

        //A classe File fornece informações de caminho, endereço de arquivos/diretórios
        //checagem de existencia, tamanho de arquivo, etc..
        //Ela não processa o arquivo
        //criando uma pasta - enviando o caminho onde ficará
        File diretorio = new File("C:\\TrabalhoSD1");

        //retorna true caso o diretório não exista e o cria no caminha indicado
        //retorna false caso exista e não executa nada
        boolean statusDir = diretorio.mkdir();
        //System.out.print(statusDir);
        return diretorio;
    }

    public boolean escreverArquivo(String nomeArquivo, ArrayList<String> dados) {
        File diretorio = criarDiretorio();

        //criando um arquivo
        //para criar um arquivo é necessário que o diretório que o comportará ja esteje criado
        //e o nome com a extensão desse novo arquivo
        File arquivo = new File(diretorio, nomeArquivo);
        try {
            //o metodo abaixo efetivamente cria o arquivo dentro do diretório passado no objeto File
            //da mesma forma, se ele não existir é criado, senão nada acontece
            boolean statusArq = arquivo.createNewFile();

            //System.out.print(statusArq);
            //a classe FileWriter prepara o arquivo para ser inserido dados
            //primeiro parâmetro é o objeto File instanciado acima
            //segundo parâmetro - true ele não sobrescreve o que ja tem no arquivo
            //false - sobrescreve tudo que havia gravado anteriormente
            FileWriter fileWriter = new FileWriter(arquivo, true);
            //essa classe é que efetivamente envia os dados para o arquivo
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String aux : dados) {
                //System.out.println(aux);
                //escrevendo uma linha com todos os dados no arquivo
                printWriter.print(aux);
                printWriter.print(";");
            }
            printWriter.println();
            //O flush indica que os dados a serem inseridos ja estão preparados para serem efetivamente inseridos no arquivo
            printWriter.flush();
            //fecha a conexão com o arquivo
            printWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //=******** MUDANDO O RETORNO DO MÉTODO **************=//
    public ArrayList<String> lerArquivo(String nomeArquivo) {
        File dir = criarDiretorio();
        File arq = new File(dir, nomeArquivo);
        ArrayList<String> auxConsultas = new ArrayList<String>();

        try {
            //Essa classe é usada quando é preciso ler dados de um arquivo
            FileReader fileReader = new FileReader(arq);

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
                auxConsultas.add(linha);

            }

            //libera-se o fluxo dos objetos
            // e fecha-se o arquivo
            fileReader.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //chamando o frame para mostrar os dados na tabela 
        return auxConsultas;
    }

    public boolean deletarLinhaArquivo(int linha, String arquivoDados) {
        //arrayList para recuperar todo conteúdo do arquivo
        ArrayList<String> auxArquivoDados = new ArrayList();
        //recuperando todo conteúdo no arquivo
        auxArquivoDados = lerArquivo(arquivoDados);
        //nessa linha é removida a informação selecionada pelo usuário da lista auxiliar
        auxArquivoDados.remove(linha - 1);

        File dir = criarDiretorio();
        File arq = new File(dir, arquivoDados);
        //o arquivo com todos os dados é deletado para ser inserido o novo (o aux acima)
        //sem a informação que o usuário deseja deletar
        arq.delete();

        for (String aux : auxArquivoDados) {
            String[] auxLinhas = aux.split(";");
            //preparando os dados para serem inseridos em um novo arquivo
            ArrayList<String> novosDados = new ArrayList();
            for (int i = 0; i < auxLinhas.length; i++) {
                novosDados.add(auxLinhas[i]);
            }
            //inserindo linha por linha no novo arquivo atualizado
            escreverArquivo(arquivoDados, novosDados);
        }
        return true;
    }

}
