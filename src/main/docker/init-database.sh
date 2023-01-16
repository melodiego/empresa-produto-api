#!/bin/bash
echo "---------------------------------------- POSTGRES ----------------------------------------"
echo "------------------------------------------------------------------------------------------"
echo "1. Iniciando Criação da Estrutura do Banco de Dados"
echo "------------------------------------------------------------------------------------------"
set -e

psql -v --single ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER postgres;
	CREATE DATABASE empresa_produto;
	GRANT ALL PRIVILEGES ON DATABASE empresa_produto TO postgres;
EOSQL

echo "------------------------------------------------------------------------------------------"
echo "1. CREATE USER postgres"
echo "------------------------------------------------------------------------------------------"
echo "2. CREATE DATABASE empresa_produto"
echo "------------------------------------------------------------------------------------------"
echo "3. GRANT ALL PRIVILEGES ON DATABASE empresa_produto TO postgres"
echo "------------------------------------------------------------------------------------------"
echo "---------------------------------------- FIM DA EXECUÇÃO POSTGRES ----------------------------------------"