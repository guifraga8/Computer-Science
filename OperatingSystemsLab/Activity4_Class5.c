#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <time.h>
#define PRINT(X) for(l=0;l<2;l++){\
                 for(m=0;m<2;m++)\
                        printf("%d ",X[l][m]); printf("\n");}\

int main(){
	
    int l,m,n;
    int pid;
    int p1[2];
    pipe(p1);

    if((pid=fork())==0){
              
        int mat1[2][2]={1,2,3,4};
    
			write(p1[1],mat1,2*2*sizeof(int));
                 
    } else {
        int mul[2][2]={0},m1[2][2],i,j,k;
                           
        read(p1[0],m1,2*2*sizeof(int));
        printf("Matrix One:\n");
        PRINT(m1);
                           
        int m2[2][2]={{5,6},{7,8}};
        printf("\nMatrix Two:\n");
        PRINT(m2);
 
        for(i=0;i<2;i++)
            for(j=0;j<2;j++)
                for(k=0;k<2;k++)
                    mul[i][j]+=m1[i][k]*m2[k][j];
        printf("\nAnswer:\n");
        PRINT(mul);
             
    }
	return 0;
}
