/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula15ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ice
 */
public class Aula15Ex01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String driverURL = "jdbc:derby://localhost:1527/2017-3-DCC171";
            
            Connection conexao = DriverManager.getConnection(driverURL,"usuario","senha");
            
            Scanner entrada = new Scanner(System.in);
            int cmd = -1;
            do{
                System.out.println("Escola uma opção (0 para sair)");
                System.out.println("\t 1 - Insere registro aleatório");
                System.out.println("\t 2 - Diminui estoque");
                System.out.println("\t 3 - Limpa estoque");
                cmd = entrada.nextInt();
                switch (cmd){
                    case 1:
                        insereRegristroAleatorio(conexao);
                        break;
                    case 2:
                        diminuiEstoques(conexao);
                        break;
                    case 3: 
                        limpaEstoque(conexao);
                        break;
                    default:
                        listaEstoque(conexao);
                        
                }
            }while(cmd !=0);
            
        } catch (Exception ex) {
            Logger.getLogger(Aula15Ex01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insereRegristroAleatorio(Connection conexao) throws SQLException {
        Random rnd = new Random();
        String sql = String.format("INSERT INTO produto (nome,qtd,atualizado) VALUES ('Produto %d',%d,CURRENT_TIMESTAMP)",rnd.nextInt(100),rnd.nextInt(10)+1);
        Statement operacao = conexao.createStatement();
        operacao.executeUpdate(sql);
        System.out.println("inserido");
                
    }

    private static void diminuiEstoques(Connection conexao) throws SQLException {
        Statement operacao = conexao.createStatement();
        int n = operacao.executeUpdate("UPDATE produto SET qtd = qtd -1 WHERE qtd>0");
        System.out.println(n+ " registros atualizados");
    }

    private static void limpaEstoque(Connection conexao) throws SQLException {
        Statement operacao = conexao.createStatement();
        int n = operacao.executeUpdate("DELETE FROM produto WHERE qtd=0");
        System.out.println(n+ " registros excluidos");
    }

    private static void listaEstoque(Connection conexao) throws SQLException{
        Statement operacao = conexao.createStatement();
        ResultSet resultado = operacao.executeQuery("SELECT nome,qtd,atualizado FROM produto");
        System.out.println("+Produto \tQTD+");
        while(resultado.next()){
            String nome = resultado.getString("nome");
            Integer qtd = resultado.getInt("qtd");
            System.out.println(String.format(" %s\t%d", nome,qtd));
            
        }
         System.out.println("+ \t+");
    }
    
}
