
package agenteracional;
import java.util.Queue;
import java.util.LinkedList;

public class AgenteRacional{
    private final char[] localizacoes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
    private final boolean[] sujeira = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    
    private void aspirar(Estado estado){
        System.out.println("Sujeira aspirada" + localizacoes[estado.posicao]);
        sujeira[estado.posicao] = false;
    }
    
    private void voltarParaCasa(Estado estado){
        System.out.println("Bolsa cheia. Agente retornou ao ponto inicial");
        estado.posicao = 0;
        estado.capacidadeBolsa = 0;
        estado.energia = 100;
    }
    
    private int calcularNovaPosicao(int posicaoAtual, char direcao){
        int linhaAtual = posicaoAtual / 4;
        int colunaAtual = posicaoAtual % 4;
        int novaPosicao = posicaoAtual;
        
        if (direcao == 'N' && linhaAtual > 0){
            novaPosicao -= 1;
        } else
            if (direcao == 'S' && linhaAtual < 3){
                novaPosicao += 1;
            } else
                if (direcao == 'L' && colunaAtual <3){
                    novaPosicao += 1;
                } else
                    if (direcao == 'O' && colunaAtual > 0){
                        novaPosicao -= 1;
                    }
        novaPosicao = Math.max(0, Math.min(15, novaPosicao));
        
        return novaPosicao;
    }
    
    public void limparQuarto(){
        Queue<Estado> fila = new LinkedList<>();
        Estado estadoInicial = new Estado(0,0,100);
        fila.add(estadoInicial);
        
        while (!fila.isEmpty()){
            Estado estadoAtual = fila.poll();
            
            if (sujeira[estadoAtual.posicao]){
                aspirar(estadoAtual);
            }
            
            if (estadoAtual.capacidadeBolsa == 10){
                voltarParaCasa(estadoAtual);
                continue;
            }
            
            for (char direcao: new char[]{'N', 'S', 'L', 'O'}){
                int novaPosicao = calcularNovaPosicao(estadoAtual.posicao, direcao);
                int novaEnergia = estadoAtual.energia;
                int novaCapacidadeBolsa = estadoAtual.capacidadeBolsa;
                
                if (sujeira[novaPosicao]){
                    novaEnergia--;
                    novaCapacidadeBolsa++;
                }
                
                Estado novoEstado = new Estado(novaPosicao, novaCapacidadeBolsa, novaEnergia);
                fila.add(novoEstado);
                    
            }
        }
    }
    public static void main(String[] args) {
       AgenteRacional agente = new AgenteRacional();
       agente.limparQuarto();
    }
    
}
