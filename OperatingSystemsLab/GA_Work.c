#include "stdio.h"
#include "stdlib.h"
#include "unistd.h"
#include "string.h"
#include "time.h"

#define READ 0
#define WRITE 1
#define TRUE 1

typedef struct tipo_produtos {
	int id;
	int tempo;
	char qualidade[4];
	char cor[10];
} TProduto;

TProduto cria_item(int i, int prod) {
	
	TProduto produto;

	produto.id = i + 5 *(prod - 1);
	produto.tempo = prod * 2;

	if (prod == 1) {
		strcpy(produto.cor, "azul");
	} else if (prod == 2) {
		strcpy(produto.cor, "vermelha");
	} else {
		strcpy(produto.cor, "amarela");
	}

	srand(time(NULL));

	if (rand() % 2 == 0) {
		strcpy(produto.qualidade, "boa");
	} else {
		strcpy(produto.qualidade, "ma");
	}

	return produto;

}

void produtor(int pp[2], int qtde_itens, int prod) {
	
	int i, bytesEscritos;

	for (i = 1; i <= qtde_itens; i++) {
		
		TProduto produto = cria_item(i, prod);

		sleep(produto.tempo);

		printf("Produtor %d criou o item %d, de cor %s e qualidade %s.\n", prod, produto.id, produto.cor, produto.qualidade);

		bytesEscritos = write(pp[WRITE], &produto, sizeof(TProduto));

		if (bytesEscritos == -1) {
			perror("Erro de escrita no pipe!");
		}
		
	}

	close(pp[WRITE]);

}

void consumidor(int pp[2], int qtde_itens) {
	
	int i, bytesLidos;

	while (TRUE) {
		
		TProduto produto;

		bytesLidos = read(pp[READ], &produto, sizeof(TProduto));
		if (bytesLidos == -1) {
			perror("Erro de leitura no pipe!");
		} else if (bytesLidos == 0) {
			break;
		}

		int pid_cons = fork();

		if (strcmp(produto.qualidade, "boa") == 0) {
			if (pid_cons == 0) {
				sleep(produto.tempo / 2);
				printf("Consumidor %d consumiu o item %d, de cor %s e qualidade %s.\n", 1, produto.id, produto.cor, produto.qualidade);
			}
		} else {
			if (pid_cons != 0) {
				sleep(produto.tempo / 2);
				printf("Consumidor %d consumiu o item %d, de cor %s e qualidade %s.\n", 2, produto.id, produto.cor, produto.qualidade);
			}
		}
		
	}

	close(pp[READ]);

}

int main() {
	
	int pp[2];

	pipe(pp);

	int pid = fork();

	if (pid == -1) {
		perror("Erro ao criar um novo processo!");
	} else if (pid == 0) {
		
		int pid_prod1 = fork();
		
		if (pid_prod1 == 0) {
			produtor(pp, 5, 1);
		} else {
			
			int pid_prod2 = fork();
			
			if (pid_prod2 == 0) {
				produtor(pp, 5, 2);
			} else {
				produtor(pp, 5, 3);
			}
		}
		
	} else {
		consumidor(pp, 15);

	}

	return 0;
}
