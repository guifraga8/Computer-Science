#!/bin/bash
dir_orig="/home/guifraga/backup/"
dir_dest="/home/guifraga/"
bkp_nome="backup_`date +%d_%m_%Y`.tgz"

touch log.txt
echo "Execução do Backup -" `date +%d/%m/%Y` > log.txt
echo "Horário de Início -" `date +%k:%M:%S` >> log.txt
echo "Arquivos compactados:" >> log.txt

tar -cvpjf ${dir_dest}${bkp_nome} ${dir_orig} >> log.txt

echo "Diretório: $dir_orig" >> log.txt
echo "Destino: ${dir_dest}${bkp_nome}" >> log.txt
echo "Horário de Finalização -" `date +%k:%M:%S` >> log.txt

echo "Backup realizado com sucesso!"
