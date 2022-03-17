#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <math.h>

#define NUM_THREADS 3
#define SECONDS 60

// Sor, o exercício não está completo... estou enviando até onde consegui fazer!

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int temperatura[SECONDS];

void *thread_C(void *argumentos) {
	pthread_mutex_lock(&mutex);

    for (int i = 0; i < SECONDS; i++) {
        temperatura[i] = (int) rand() % 40 + 20;
        printf("Temperatura: %i °C\n", temperatura[i]);
        sleep(1);
    }
    
    pthread_mutex_unlock(&mutex);
    
}

void *thread_P(void *argumentos) {
	int soma = 0;
    double media = 0;

    pthread_mutex_lock(&mutex);

    for (int i = 0; i < SECONDS; i++){
        soma+=temperatura[i];
    }
    
    media = soma/SECONDS;
    pthread_mutex_unlock(&mutex);
    printf("Temperatura média: %f.", media);
	pthread_exit(NULL);
}

void *thread_M(void *argumentos) {
	printf("\nReceber média e comparar com linear aqui\n");
	pthread_exit(NULL);
}

int main () {
	pthread_t threads[NUM_THREADS];
	int erro;

    printf("Criando thread_C\n");
    erro = pthread_create(&threads[0], NULL, thread_C, NULL);
    if (erro) {
        printf("Erro ao criar thread\n");
        exit(-1);
    }
    
    printf("Criando thread_P\n");
    erro = pthread_create(&threads[1], NULL, thread_P, NULL);
    if (erro) {
        printf("Erro ao criar thread\n");
        exit(-1);
    }
    
    printf("Criando thread_M\n");
    erro = pthread_create(&threads[2], NULL, thread_M, NULL);
    if (erro) {
        printf("Erro ao criar thread\n");
        exit(-1);
    }
    
	pthread_exit(NULL);

	return 0;
}