#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

int main (int argc , char ** argv ) {
	int pid = fork () ; /* cria outro processo */
	
	if ( pid < 0) { /* ocorreu erro na execucao do Fork */
		fprintf ( stderr , " Falha no Fork ");
		exit ( -1) ;
	} else if ( pid == 0) { /* processo filho */
		execlp ("/ bin / ls " ," ls " , NULL );
		exit (1) ; // so eh executado se execlp falhar
	} else { /* processo pai */
		wait (0) ; /* pai espera o termino do filho */
		printf (" Filho terminou ");
		exit (0) ;
	}
}