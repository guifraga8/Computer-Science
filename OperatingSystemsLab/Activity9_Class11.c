// gcc -pthread -o Atividade9_Aula11_Guilherme_Fraga Atividade9_Aula11_Guilherme_Fraga.c
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>

#define N 5
#define LEFT (i+N-1)%N
#define RIGHT (i+1)%N
#define THINKING 0
#define HUNGRY 1
#define EATING 2

int state[N], i, int_rand;
float float_rand;

// protótipos das funções
void mostrar(void);
void pensar(int);
void pegar_garfo(int);
void por_garfo(int);
void comer(int);
void test(int);
void *acao_filosofo(void *);
void estatistica(void);

// semafóros
sem_t mutex;
sem_t sem_fil[N];

typedef struct Filosofo {
    int tentativa;
    int comeu;
} Filosofo;

Filosofo filosofos[N];

int main(){
    for(i=0; i<N; i++){
        state[i]=0;
        filosofos[i].comeu = 0;
        filosofos[i].tentativa = 0;
    }
    int res;
    pthread_t thread[N];
    void *thread_result;
    // inicia os semáforos
    res = sem_init(&mutex,0,1);
    if(res!=0){
        perror("Erro na inicialização do semáforo!");
        exit(EXIT_FAILURE);
    }
    for(i=0; i<N; i++){
        res = sem_init(&sem_fil[i],0,0);
        if(res!=0){
            perror("Erro na inicialização do semáforo!");
            exit(EXIT_FAILURE);
        }
    }
    // cria as threads (filósofos)
    for(i=0; i<N; i++){
        res = pthread_create(&thread[i],NULL,acao_filosofo,&i); // o argumento que será passado para a thread é &i
        if(res!=0){
            perror("Erro na inicialização da thread!");
            exit(EXIT_FAILURE);
        }
    }
    // faz um join nas threads
    for(i=0; i<N; i++){
        res = pthread_join(thread[i],&thread_result);
        if(res!=0){
            perror("Erro ao fazer join nas threads!");
            exit(EXIT_FAILURE);
        }
    }
    estatistica();
    return 0;
}

// função que mostra o estado dos N filósofos
void mostrar(){
    for(i=0; i<N; i++){
        if(state[i] == THINKING){
            printf("O filósofo %d está pensando!\n", i);
        }
        if(state[i] == HUNGRY){
            printf("O filósofo %d está com fome!\n", i);
        }
        if(state[i] == EATING){
            printf("O filósofo %d está comendo!\n", i);
        }
    }
    printf("\n");
}

// ação do filósofo
void *acao_filosofo(void *j){
    int i = *(int*) j;
    time_t endwait;
    time_t start = time(NULL);
    time_t seconds = 10;
    endwait = start + seconds;
    while(start < endwait){
        pensar(i);
        pegar_garfo(i);
        comer(i);
        por_garfo(i);
        start = time(NULL);
    }
}

// a thread (filósofo) espera um tempo aleatório pensando ou comendo
void pensar(int i){
    float_rand=0.001*random();
    int_rand=float_rand;
    usleep(int_rand);
}

void pegar_garfo(int i){
    sem_wait(&mutex);
    state[i]=HUNGRY;
    mostrar();
    test(i);
    sem_post(&mutex);
    sem_wait(&sem_fil[i]);
}

// função que testa se o filósofo pode comer
void test(int i){
    if(state[i] == HUNGRY && state[LEFT] != EATING && state[RIGHT] != EATING){
        state[i]=EATING;
        filosofos[i].comeu++;
        mostrar();
        sem_post(&sem_fil[i]);
    }
    filosofos[i].tentativa++;
}

void comer(int i){
    float_rand=0.001*random();
    int_rand=float_rand;
    usleep(int_rand);
}

void por_garfo(int i){
    sem_wait(&mutex);
    state[i]=THINKING;
    mostrar();
    test(LEFT);
    test(RIGHT);
    sem_post(&mutex);
}

void estatistica(){
    printf("Estatística do jantar dos filósofos:\n\n");
    for(i=0; i<N; i++){
        printf("Filósofo %d tentou comer %d, mas só comeu %d refeições.\n", i, filosofos[i].tentativa, filosofos[i].comeu);    
    }
}
