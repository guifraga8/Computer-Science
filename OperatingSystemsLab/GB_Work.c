#include <stdio.h>
#include <stdlib.h> 
#include <time.h>
#include <pthread.h>
#include <semaphore.h> 
#include <unistd.h>
#include <math.h>
#include <sys/types.h>

#define TamanhoFisico 8 // 64 KB/8 KB = 8 frames
#define TamanhoVirtual 128 // 1 MB/8 KB = 128 páginas
#define Nao -1
#define Sim 1
#define Num_Threads 2
#define KBinByte 8192 // conversão

sem_t mutex;

typedef struct LinhaTabela {
    int frameUsado; //-1 NAO 1 SIM
    int paginaAtual;
    int numBuscas;
    int tempo; // "tempo" de reserva para ser trocado
} LinhaTabela;

LinhaTabela TabelaDePaginas[TamanhoFisico]; // 8 linhas (8 frames)

int estaNaTabela(int pagina) {
    for (int i = 0; i < TamanhoFisico; i++) {
        if (TabelaDePaginas[i].frameUsado == Sim) {
            if (TabelaDePaginas[i].paginaAtual == pagina) {
                printf("\nPágina %d encontrada no frame %d\n", pagina, i);
                return i;
            }
        }
    }
    return Nao;
}

int existeFrameLivre() {
    for (int i = 0; i < TamanhoFisico; i++) {
        if (TabelaDePaginas[i].frameUsado == Nao) {
            return i;
        }
    }
    return Nao;
}

LinhaTabela adicionarNaTabela(int pagina, LinhaTabela linha) {
    linha.frameUsado = Sim;
    linha.paginaAtual = pagina;
    return linha;
}

int encontrarMenosBuscado() {
    int menosBuscado, vezesBuscadas;

    // descobre o primeiro que está com tempo zero
    for (int i = 0; i < TamanhoFisico; i++){
	    if (TabelaDePaginas[i].tempo != 0) {
	        continue;
	    }
	
	    menosBuscado = i;
    	vezesBuscadas = TabelaDePaginas[i].numBuscas;
	    break;
    }

    // descobre qual foi buscado menos vezes
    for (int i = menosBuscado + 1; i < TamanhoFisico; i++) {
        if (TabelaDePaginas[i].numBuscas < vezesBuscadas) {
            if (TabelaDePaginas[i].tempo == 0) {
                menosBuscado = i;
                vezesBuscadas = TabelaDePaginas[i].numBuscas;
            }
        }
    }
    return menosBuscado;
}

LinhaTabela diminuiTempo(LinhaTabela linha) {
    if (linha.tempo != 0) {
        linha.tempo = linha.tempo - 1;
    }
    return linha;
}

void *acao_thread(void *k) {
    pthread_t j = pthread_self(); // pegar um id da thread (do sistema) para diferenciar
    int menorTamanho = 1, maiorTamanho = KBinByte * TamanhoVirtual, numChamadas = 2;
    srand(time(0)); // número aleatório
    
    for (int i = 0; i < numChamadas; i++) { 
        int numBytes = (rand() % (maiorTamanho - menorTamanho + 1)) + menorTamanho; // pegar número aleatório entre maior tamanho e menor
        int numPag = ceil(numBytes / KBinByte); // transforma o valor em número de páginas e arredonda pra cima (ceil)
		
		sem_wait(&mutex);
        printf("-----------------------------------------------------------------------------------------------");
		printf("\nThread %lu deseja acessar %d byte(s) de dados na memória ou %d página(s).\n", j, numBytes, numPag);
        	
		for (int pagAtual = 0; pagAtual<numPag; pagAtual++) {	
			int encontrado = estaNaTabela(pagAtual);

			if (encontrado == Nao) {
				printf("\nFalta de Página!\n");
		    
				int frameLivre = existeFrameLivre();
				if (frameLivre != Nao) {
					printf("\nFrame %d está livre\n", frameLivre);
		        
					TabelaDePaginas[frameLivre] = adicionarNaTabela(pagAtual, TabelaDePaginas[frameLivre]);
					printf("\nPágina %d copiada para o frame %d\n", pagAtual, frameLivre);
		        
					TabelaDePaginas[frameLivre].numBuscas = 1;
					TabelaDePaginas[frameLivre].tempo = 5;
				} else {
					printf("\nNão existem frames livres\n");
					int menosBuscado = encontrarMenosBuscado();
					TabelaDePaginas[menosBuscado] = adicionarNaTabela(pagAtual, TabelaDePaginas[menosBuscado]);
					TabelaDePaginas[menosBuscado].numBuscas = 1;
					TabelaDePaginas[menosBuscado].tempo = 5;
					printf("\nPágina %d copiada para o frame %d\n", pagAtual, menosBuscado);
				}
			} else {
				TabelaDePaginas[encontrado].numBuscas++;
			}
			
			printf("\n");
			for (int i = 0; i<TamanhoFisico; i++) {
				TabelaDePaginas[i] = diminuiTempo(TabelaDePaginas[i]);
				printf("Frame %d foi buscado %d vezes, contém a página %d e está com tempo %d\n", i, TabelaDePaginas[i].numBuscas, TabelaDePaginas[i].paginaAtual,TabelaDePaginas[i].tempo);
			}
        	
		}
		sem_post(&mutex);
    }
}

int main() {
    
    for (int i = 0; i<TamanhoFisico; i++) {
        TabelaDePaginas[i].frameUsado = Nao;
        TabelaDePaginas[i].paginaAtual = -1;
        TabelaDePaginas[i].numBuscas = 0;
        TabelaDePaginas[i].tempo = 0;
    }

    int res;
    res = sem_init(&mutex, 0, 1);
    
    if (res!=0) {
        perror("Erro na inicialização do semáforo!");
    	exit(EXIT_FAILURE);
    }
	
    pthread_t thread[Num_Threads];
    void *thread_result;
	
    // cria as threads
    for (int i = 0; i < Num_Threads; i++) {
        res = pthread_create(&thread[i],NULL,acao_thread,&i);
        if (res != 0) {
            perror("Erro na inicialização da thread!");
            exit(EXIT_FAILURE);
        }
    }
    
    // faz um join nas threads
    for (int i = 0; i < Num_Threads; i++) {
        res = pthread_join(thread[i],&thread_result);
        if (res!=0) {
            perror("Erro ao fazer join nas threads!");
            exit(EXIT_FAILURE);
        }
    }
    return 0;
}
