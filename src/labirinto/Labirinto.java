package labirinto;
import java.util.Random;
import java.util.Arrays;

public class Labirinto {

    public static void main(String[] args) {
       
        int inicial[] = {0,1,2,3};  //Criação das 4 direções posíveis de movimento
                                    //0 = esquerda, 1 = direita, 2 = para cima, 3 = para baixo
        
        
        int tamanhoPop = 1000;                     //Tamanho da população
        int taxMutacao = (tamanhoPop/100)*1;      //Taxa de mutação dentro da população
        int taxSelecao = (tamanhoPop/100)*60;     //Seleção de indivíduos mais aptos
        int taxElitismo = (tamanhoPop/100)*10;    //Elitismo
                
        int[][] populacao = new int[tamanhoPop][8]; //Matriz população
        int nota[] = new int[tamanhoPop];           //Vetor de notas de avaliação
        
        int numGeracoes = 0;                      
        int fim = 100;                            
        
        for (int i=0; i < tamanhoPop; i++){     //Criação de indivíduos iniciais da população com um valor aleatório para 
            for (int j=0; j < 8; j++){          //seu código genético individual
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(4);
                populacao[i][j] = inicial[randomInt];
            }
            
        }
        
        while (fim != 0){     //Função principal com critério de parada
            avaliacao(populacao, nota); 
            ordenacao(populacao, nota);
            novaPop(populacao, nota, taxSelecao, taxElitismo);
            mutacao(populacao, taxMutacao);
        
                fim = nota[0];      //Critério de para quando o valor do indivíduo melhor avaliado for zero (passagem perfeita)
                numGeracoes ++;     //Incremento para o número de gerações 
        }
    // System.out.println(Arrays.deepToString(populacao)); //Impressão da última população quando a resposta é encontrada
    // System.out.println(Arrays.toString(nota));          //Impressão da nota dos indivíduos da última população quando a resposta é encontrada
        
     System.out.println("Resposta encontrada: ");       //Impressão da resposta encontrada
     for (int i=0; i < 8; i ++){
         if(populacao[0][i] == 0){
             System.out.println("Esquerda (<) ");
         }
         if(populacao[0][i] == 1){
             System.out.println("Direita (>) ");
         }
         if(populacao[0][i] == 2){
             System.out.println("Para cima (^) ");
         }
         if(populacao[0][i] == 3){
             System.out.println("Para baixo (v) ");
         }
    }
         //Impressão do número de gerações necessárias para chegar na resposta
     System.out.println("Números de gerações para encontrar a resposta: " + numGeracoes);  
    };
        
    private static void avaliacao(int [][] pop, int [] nt){   //Método para avaliação de cada indivíduo na população (Fitness)
         
       /*
            Desenho da matriz para entendimento:
        
                           0    1   2   3   4     
                           5    6   7   8   9
                           10   11  12  13  14
                           15   16  17  18  19
                           20   21  22  23  24
        
        O indivíduo recebe o valor inicial da posição "0"
        Ao somar 1 a sua posição, ele move-se para a direita.
        Ao subrair 1 de sua posição, ele move-se para a esquerda.
        Ao somar 5 a sua posição, ele move-se para baixo.
        Ao subtrair 5 de sua posição, ele move-se para cima.
        */
        
        
        //Barreiras e limites do labirinto para cada casa
        
        int esquerda [] =       {3,4,6,8,17,18};
        int direita [] =        {2,3,5,7,16,17};
        int acima [] =          {6,12,13,15,19,21,23};
        int abaixo[] =          {1,7,8,10,14,16,18};
        int limiteAcima[] =     {0,1,2,3,4};
        int limiteAbaixo[] =    {20,21,22,23,24};
        int limiteEsquerda[] =  {0,5,10,15,20};
        int limiteDireita[] =   {4,9,14,19};
               
        //Avaliação de nota pelo desempenho no labirinto

        //Passar por uma parede +5 pontos
        //Passar por fora do limite do labirinto +20 pontos
        //Andar no sentido oposto da saída +1 pontos
        //Terminar o percurso sem chegar a saída +8 pontos
        
        for (int i=0; i < pop.length; i++){
            int notaTemp = 0;
            int posicao = 0;
            for (int j=0; j < 8; j++){
                
                if(pop[i][j]==0){
                    for (int k=0; k < esquerda.length; k++){
                        if(posicao == esquerda[k]){
                            notaTemp = notaTemp + 5;
                            break;
                        }
                    }
                    for (int k=0; k < limiteEsquerda.length; k++){
                        if(posicao == limiteEsquerda[k]){
                            notaTemp = notaTemp + 20;
                            break;
                        }
                    }
                    posicao = posicao -1;
                    notaTemp = notaTemp + 1;
                }
                if(pop[i][j]==1){
                    for (int k=0; k < direita.length; k++){
                        if(posicao == direita[k]){
                            notaTemp = notaTemp + 5;
                            break;
                        }
                    }
                    for (int k=0; k < limiteDireita.length; k++){
                        if(posicao == limiteDireita[k]){
                            notaTemp = notaTemp + 20;
                            break;
                        }
                    }
                    posicao = posicao +1;
                }
                if(pop[i][j]==2){
                    for (int k=0; k < acima.length; k++){
                        if(posicao == acima[k]){
                            notaTemp = notaTemp + 5;
                            break;
                        }
                    }
                    for (int k=0; k < limiteAcima.length; k++){
                        if(posicao == limiteAcima[k]){
                            notaTemp = notaTemp + 20;
                        }
                    }
                     posicao = posicao -5;
                    notaTemp = notaTemp + 1;
                }
                if(pop[i][j]==3){
                    for (int k=0; k < abaixo.length; k++){
                        if(posicao == abaixo[k]){
                            notaTemp = notaTemp + 5;
                            break;
                        }
                    }
                    for (int k=0; k < limiteAbaixo.length; k++){
                        if(posicao == limiteAbaixo[k]){
                            notaTemp = notaTemp + 20;
                            break;
                        }
                    }
                    posicao = posicao +5;
                }
            }
            if(posicao != 24){
                    notaTemp = notaTemp + 8;
                }
            nt[i] = notaTemp;
        }
         
    }
    
    private static void ordenacao(int [][] pop, int [] nt){     //Método que ordena os indivíduos da população por ordem crescente da avaliação
        
         for (int k=0; k < pop.length - 1; k++){
            for (int i=0; i < pop.length - 1; i++){     //ordenação da população pela nota avaliação
        
                if(nt[i+1]<nt[i]){
                    int[]temp = new int[8];
                    int[] temp2 = new int[pop.length];
                    for (int j=0; j < 8; j++){
                        temp[j] = pop[i+1][j];
                        pop[i+1][j] = pop[i][j];
                        pop[i][j] = temp[j];
                    }
                    temp2[i] = nt[i+1];
                    nt[i+1] = nt[i];
                    nt[i] = temp2[i];
                }
            }
        }
    }
    
    private static void novaPop(int [][] pop, int [] nt, int selecao, int elitismo){    //Método que cria a nova geração de população
        
      
        int filhoS[][] = new int [pop.length - elitismo][8];
        int filho[][] = new int[2][8];
              
        for(int i=0; i < (pop.length - elitismo)/2; i++){
            
            Random randomGenerator = new Random();
            
            int[][] pai = new int[2][8];        //Matriz 2x8 para os pais selecionados por Torneio
            
            for(int p=0; p < 2; p++){
                int randomInt1 = randomGenerator.nextInt(selecao);  //Escolha aleatória de dois candidatos a pais 
                int randomInt2 = randomGenerator.nextInt(selecao);  //dentro do limite de seleção pré escolhido
                int A = nt[randomInt1];
                int B = nt[randomInt2];
                
                if(A<B){                                            //Seleção do pai. processo se repete 2 vezes
                    for (int j=0; j < 8; j++){                      //para a escolha de dois pais que gerarão 1
                        pai[p][j] = pop[randomInt1][j];             //filho
                    }
                }else{
                    for (int j=0; j < 8; j++){
                        pai[p][j] = pop[randomInt2][j];
                    }
                }
            }
                        
            for (int x=0; x < 4; x++){          //Filho recebe metade dos genes de cada pai (Crossover de dois pontos)
                filho[0][x] = pai[0][x];
                filho[0][x+4] = pai[1][x+4];
                
                filho[1][x] = pai[1][x];
                filho[1][x+4] = pai[0][x+4];
            }
            for (int j=0; j < 8; j++){          //Inserção dos filhos no vetor "filhoS"
                filhoS[i][j]=filho[0][j];
                filhoS[((pop.length - elitismo)/2) + i][j]=filho[1][j];
            }
        }
          
        for (int i= elitismo; i < pop.length; i++){         //População inserida para nova geração conservando o elitismo
            for (int j=0; j < 8; j++){
                pop[i][j] = filhoS[i-elitismo][j];
            }
        }
    }
    
    private static void mutacao(int[][] pop, int mutacao){
         for (int i=0; i < mutacao; i++){                 //Inclusão da mutação em indivíduo e gene aleatório 
            Random randomGenerator = new Random();       //com taxa pré definida
            int randomInt = randomGenerator.nextInt(pop.length);
            int randomGene = randomGenerator.nextInt(8);
            int mut = randomGenerator.nextInt(4);
            pop[randomInt][randomGene] = mut;

        }
    }
}
